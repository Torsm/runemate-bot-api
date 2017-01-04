package net.celestialproductions.api.util;

import java.util.Comparator;

/**
 * Compares enum entities by their ordinal value.
 * Higher ordinal values get prioritized.
 * If one enum is null, the other enum is automatically prioritized.
 *
 * @author Savior
 */
public final class EnumComparator implements Comparator<Enum> {

    @Override
    public int compare(final Enum o1, final Enum o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return 1;
        } else if (o2 == null) {
            return -1;
        } else {
            return o2.ordinal() - o1.ordinal();
        }
    }
}
