package net.celestialproductions.api.game.location;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.util.calculations.Distance;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Savior
 */
public class DistanceComparator implements Comparator<Locatable> {
    private final Map<Locatable, Double> cache = new HashMap<>();

    @Override
    public int compare(final Locatable l1, final Locatable l2) {
        final double delta = getDistance(l1) - getDistance(l2);
        return delta < 0 ? -1 : delta == 0 ? 0 : 1;
    }

    private Double getDistance(final Locatable l) {
        return cache.computeIfAbsent(l, k -> Distance.to(l));
    }
}
