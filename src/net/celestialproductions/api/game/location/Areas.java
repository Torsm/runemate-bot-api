package net.celestialproductions.api.game.location;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.entities.details.Rotatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Defeat3d
 */
public final class Areas {

    public static Area.Rectangular getSurroundingArea(final Locatable loc, final int radius) {
        final int x = loc.getPosition().getX();
        final int y = loc.getPosition().getY();
        return new Area.Rectangular(new Coordinate(x - radius, y - radius, 0), new Coordinate(x + radius, y + radius, 0));
    }

    public static <T extends Rotatable & Locatable> Coordinate getFacingCoordinate(final T rotatable) {
        return getFacingCoordinate(rotatable, rotatable, 0);
    }

    public static <T extends Rotatable & Locatable> Coordinate getFacingCoordinate(final T rotatable, int rotationOffset) {
        return getFacingCoordinate(rotatable, rotatable, rotationOffset);
    }

    public static Coordinate getFacingCoordinate(final Rotatable rotatable, final Locatable locatable) {
        return getFacingCoordinate(rotatable, locatable, 0);
    }

    public static Coordinate getFacingCoordinate(final Rotatable rotatable, final Locatable locatable, int rotationOffset) {
        final int dx, dy;
        final int angle = (rotatable.getOrientationAsAngle() + rotationOffset) % 360;
        switch (angle) {
            case 0:
                dx = 0;
                dy = 1;
                break;
            case 90:
                dx = 1;
                dy = 0;
                break;
            case 180:
                dx = 0;
                dy = -1;
                break;
            default:
                dx = -1;
                dy = 0;
        }

        return locatable.getPosition().derive(dx, dy);
    }

    public static Coordinate getNearestReachableCoordinateWithin(final Locatable locatable, final Area area) {
        final DistanceComparator comparator = new DistanceComparator(locatable);
        List<Coordinate> coordinates = area.getCoordinates();
        //coordinates.sort(comparator);
        return coordinates.get(0);
    }

    public static Collection<Coordinate> getSurroundingWeb(final Locatable loc, final int radius, final boolean divide, final int divisibleBy) {
        if (divide) {
            final Collection<Coordinate> reachable = loc.getPosition().getReachableCoordinates();
            return reachable.stream().filter(c -> c.getX() % divisibleBy == 0 && c.getY() % divisibleBy == 0 && c.distanceTo(loc) <= radius).collect(Collectors.toList());
        }
        return loc.getPosition().getReachableCoordinates();
    }

    public static Area.Rectangular enlarge(final Area.Rectangular area, final int increment) {
        final Coordinate oldBottomLeft = area.getBottomLeft();
        final Coordinate newBottomLeft = new Coordinate(oldBottomLeft.getX() - increment, oldBottomLeft.getY() - increment, oldBottomLeft.getPlane());
        final Coordinate oldTopRight = area.getTopRight();
        final Coordinate newTopRight = new Coordinate(oldTopRight.getX() + increment, oldTopRight.getY() + increment, oldTopRight.getPlane());
        return new Area.Rectangular(newBottomLeft, newTopRight);
    }
}
