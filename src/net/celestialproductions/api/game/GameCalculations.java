package net.celestialproductions.api.game;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;

import java.awt.*;

/**
 * @author Savior
 */
public final class GameCalculations {

    public static double cameraAngleTo(final Locatable locatable) {
        final Player local = Players.getLocal();
        final Coordinate coord1 = local == null ? null : local.getPosition();
        final Coordinate coord2 = locatable == null ? null : locatable.getPosition();

        if (coord1 == null || coord2 == null) {
            return -1;
        }

        final double deltaX = coord2.getX() - coord1.getX();
        final double deltaY = coord2.getY() - coord1.getY();

        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX) - Math.atan2(1, 0));
        if (angle < -180D) {
            angle = 360D + angle;
        }

        double yaw = Camera.getYaw() - 180D;

        return Math.abs(180D - Math.abs(yaw - angle));
    }

    public static InteractablePoint deviatedMousePosition(final int xMin, final int xMax, final int yMin, final int yMax) {
        final Point cur = Mouse.getPosition();
        final int devX = Random.nextInt(xMin, xMax) + cur.x;
        final int devY = Random.nextInt(yMin, yMax) + cur.y;
        return new InteractablePoint(devX, devY);
    }

    public static int deviatedCameraAngle(final int min, final int max) {
        int yaw = Camera.getYaw();
        yaw += Random.nextInt(min, max) + 360;
        return yaw % 360;
    }
}
