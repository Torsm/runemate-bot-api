package net.celestialproductions.api.game.location.traversal;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.location.navigation.web.WebPathBuilder;
import com.runemate.game.api.hybrid.region.Region;

/**
 * @author Savior
 */
public final class Pathing {

    public static Path buildSafePathTo(final Locatable destination) {
        return buildSafePathTo(destination, Traversal.getDefaultWeb().getPathBuilder(), false);
    }

    public static Path buildSafePathTo(final Locatable destination, final boolean hasObstacles) {
        return buildSafePathTo(destination, Traversal.getDefaultWeb().getPathBuilder(), hasObstacles);
    }

    public static Path buildSafePathTo(final Locatable destination, final WebPathBuilder builder) {
        return buildSafePathTo(destination, builder, false);
    }

    public static Path buildSafePathTo(final Locatable destination, final WebPathBuilder builder, final boolean hasObstacles) {
        Path optional = null;
        if (builder != null)
            optional = builder.buildTo(destination);

        final Area area;
        if (optional == null && (area = Region.getArea()) != null && area.contains(destination))
            optional = RegionPath.buildTo(destination);

        if (optional == null && !hasObstacles)
            optional = BresenhamPath.buildTo(destination);

        return optional;
    }
}
