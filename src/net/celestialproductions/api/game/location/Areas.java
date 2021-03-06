package net.celestialproductions.api.game.location;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.entities.details.Rotatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

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
        return getFacingCoordinate(rotatable, 0);
    }

    public static <T extends Rotatable & Locatable> Coordinate getFacingCoordinate(final T rotatable, int rotationOffset) {
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

        return rotatable.getPosition().derive(dx, dy);
    }

    public static Area.Rectangular enlarge(final Area.Rectangular area, final int increment) {
        final Coordinate oldBottomLeft = area.getBottomLeft();
        final Coordinate newBottomLeft = new Coordinate(oldBottomLeft.getX() - increment, oldBottomLeft.getY() - increment, oldBottomLeft.getPlane());
        final Coordinate oldTopRight = area.getTopRight();
        final Coordinate newTopRight = new Coordinate(oldTopRight.getX() + increment, oldTopRight.getY() + increment, oldTopRight.getPlane());
        return new Area.Rectangular(newBottomLeft, newTopRight);
    }
}
