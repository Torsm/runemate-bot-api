package net.celestialproductions.api.game.entities.npc;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.queries.NpcQueryBuilder;
import com.runemate.game.api.hybrid.region.Players;
import net.celestialproductions.api.game.entities.TimeableEntity;

import java.util.concurrent.Callable;

/**
 * @author Savior
 */
public class TimeableNpc extends TimeableEntity<Npc, NpcQueryBuilder> {

    public TimeableNpc(final Coordinate position, final Callable<NpcQueryBuilder> builder) {
        super(position, builder);

        setFixedSpawnRate(false);
    }

    public boolean isTargeted() {
        final Npc instance = instance();
        final Player local;
        return instance != null && (local = Players.getLocal()) != null && instance.equals(local.getTarget());
    }

    @Override
    public boolean isWithinLoadingDistance() {
        final Player local = Players.getLocal();
        if (local != null) {
            final int dx = Math.abs(getPosition().getX() - local.getPosition().getX());
            final int dy = Math.abs(getPosition().getY() - local.getPosition().getY());
            return Math.max(dx, dy) <= 15;
        }
        return false;
    }
}
