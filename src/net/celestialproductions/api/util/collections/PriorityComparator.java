package net.celestialproductions.api.util.collections;

import java.util.Comparator;

/**
 * @author Savior
 */
public class PriorityComparator implements Comparator<Prioritizable> {
    @Override
    public int compare(final Prioritizable p1, final Prioritizable p2) {
        return p2.priority() - p1.priority();
    }
}
