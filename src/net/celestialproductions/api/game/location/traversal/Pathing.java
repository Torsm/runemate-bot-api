package net.celestialproductions.api.game.location.traversal;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPathBuilder;
import com.runemate.game.api.hybrid.region.Region;
import com.runemate.game.api.hybrid.util.calculations.Distance;

/**
 * @author Savior
 */
public final class Pathing {

    public static Path buildSafePathTo(final Locatable destination, final WebPathBuilder builder) {
        Path optional = null;
        if (builder != null) {
            optional = builder.buildTo(destination);
        }

        final Area area;
        if (optional == null && (area = Region.getArea()) != null && area.contains(destination)) {
            optional = RegionPath.buildTo(destination);
        }

        if (optional == null) {
            optional = BresenhamPath.buildTo(destination);
        }

        return optional;
    }

    public static Path buildSafePathTo(final Locatable destination) {
        return buildSafePathTo(destination, null);
    }

    public static double totalDistance(final Path path, final Distance.Algorithm algorithm) {
        if (path == null || algorithm == null) {
            return 0;
        }

        double distance = 0;
        Locatable last = null;

        for (Locatable l : path.getVertices()) {
            if (last != null) {
                distance += Distance.between(l, last, algorithm);
            }
            last = l;
        }

        return distance;
    }

    public static double totalDistance(final Path path) {
        return totalDistance(path, Distance.getDefaultAlgorithm());
    }

}
