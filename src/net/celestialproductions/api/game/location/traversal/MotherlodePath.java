package net.celestialproductions.api.game.location.traversal;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.player_sense.PlayerSense;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.region.Region;
import com.runemate.game.api.hybrid.util.calculations.Distance;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import net.celestialproductions.api.bot.settings.Setting;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Savior
 *
 * TODO: rewrite this mess
 */
public class MotherlodePath extends Path {
    private final static Area MOTHERLODE_REGION = new Area.Rectangular(new Coordinate(3650, 5550, 0), new Coordinate(3780, 5700, 0));
    private final static Pattern PATTERN = Pattern.compile("(Mine|Climb)");
    private final Setting<Integer> DISTANCE_SETTING = new Setting<>("stepDistance", Setting.INTEGER_STRING_CONVERTER);
    private final List<Coordinate> vertices;
    private int deviation = 0;

    private MotherlodePath(final List<Coordinate> vertices) {
        this.vertices = vertices;
    }

    @Override
    public boolean step(final TraversalOption... traversalOptions) {
        final List<TraversalOption> options = Arrays.asList(traversalOptions);
        final Player local = Players.getLocal();

        final Coordinate destination;
        if (options.contains(TraversalOption.MANAGE_DISTANCE_BETWEEN_STEPS) && (destination = Traversal.getDestination()) != null && local != null && destination.distanceTo(local) > DISTANCE_SETTING.get(Random.nextInt(6, 13))) {
            return false;
        }

        if (options.contains(TraversalOption.MANAGE_RUN) && !Traversal.isRunEnabled() && PlayerSense.getAsInteger(PlayerSense.Key.ENABLE_RUN_AT) < Traversal.getRunEnergy()) {
            Traversal.toggleRun();
        }

        final Locatable next = getNext();
        if (next != null) {
            if (next instanceof GameObject) {
                final GameObject obstacle = (GameObject) next;
                final GameObjectDefinition def = obstacle.getDefinition();
                final String name = def == null ? null : def.getName();
                if (name != null && name.equals("Ladder")) {
                    obstacle.setForcedModel(new int[]{-25, -50, 35}, new int[]{40, -150, 30});
                }
                if (!obstacle.isVisible()) {
                    Camera.turnTo(obstacle, Random.nextDouble(0.3, Camera.getPitch()));
                }
                if (Distance.to(obstacle) <  Random.nextInt(8, 13) && obstacle.getVisibility() >= 90) {
                    if (obstacle.interact(PATTERN)) {
                        if (local != null) {
                            local.getPosition().randomize(10, 10).minimap().hover();
                            Execution.delayUntil(() -> local.getAnimationId() == -1 && !obstacle.isValid(), local::isMoving, 1200, 1800);
                        }
                        return true;
                    }
                    return false;
                }
            }

            Coordinate coordinate = next.getPosition();
            if (deviation > 0)
                coordinate = coordinate.randomize(deviation, deviation);
            return coordinate.minimap().click();
        }
        return false;
    }

    @Override
    public List<? extends Locatable> getVertices() {
        return vertices;
    }

    @Override
    public Locatable getNext() {
        final Area.Rectangular region = Region.getArea();
        if (vertices.isEmpty() || region == null)
            return null;

        Locatable l = null;
        final HashMap<String, Object> cache = new HashMap<>(20);
        for (Coordinate current : vertices) {
            final GameObject obstacle = GameObjects.newQuery().on(current).names("Rockfall", "Ladder").results().first();
            if (current.minimap().isVisible(region, cache)) {
                if (obstacle != null) {
                    return obstacle;
                } else {
                    l = current;
                }
            } else if (obstacle != null) {
                return l;
            }
        }

        return l;
    }

    public static MotherlodePath buildTo(Locatable... destinations) {
        return destinations != null && destinations.length != 0 ? buildTo(Arrays.asList(destinations)) : null;
    }

    public static MotherlodePath buildTo(Collection<? extends Locatable> destinations) {
        return destinations != null && !destinations.isEmpty() ? buildBetween(Players.getLocal(), destinations) : null;
    }

    public static MotherlodePath buildBetween(Locatable startLocatable, Collection<? extends Locatable> destinations) {
        if (startLocatable == null || destinations == null || destinations.isEmpty())
            return null;

        final Coordinate start = startLocatable.getPosition();
        if (start == null)
            return null;

        ArrayList<Locatable> validTargets = new ArrayList<>(destinations.size());
        destinations.stream().filter(l -> l.getPosition() != null).forEach(validTargets::add);

        validTargets.trimToSize();
        if (validTargets.isEmpty())
            return null;

        final Coordinate base = Region.getBase();
        if (base == null || !MOTHERLODE_REGION.contains(base))
            return null;

        final int[][][] collisionFlags = Region.getCollisionFlags();
        GameObjects.newQuery().names("Rockfall", "Ladder").results().stream().map(o -> o.getPosition().derive(-base.getX(), -base.getY())).forEach(c -> {
            final int x = c.getX();
            final int y = c.getY();
            if (x >= 0 && y >= 0 && x < collisionFlags[0].length && y < collisionFlags[0][0].length)
                collisionFlags[0][x][y] = 0;
        });
        final RegionPath path = RegionPath.buildBetween(start, destinations, collisionFlags);

        if (path == null)
            return null;

        return new MotherlodePath(path.getVertices());
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }
}
