package net.celestialproductions.api.game.location;

import com.runemate.game.api.hybrid.entities.details.Locatable;

import java.util.Comparator;

/**
 * @author Savior
 */
public class DistanceComparator implements Comparator<Locatable> {
    private final Locatable locatable;

    public DistanceComparator(final Locatable locatable) {
        this.locatable = locatable;
    }

    @Override
    public int compare(final Locatable o1, final Locatable o2) {
        return o1.distanceTo(locatable) < o2.distanceTo(locatable) ? -1 : o1.distanceTo(locatable) == o2.distanceTo(locatable) ? 0 : 1;
    }

}
