package net.celestialproductions.api.game.entities;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * Concurrency safe collection to store timeable entities in
 *
 * @author Defeat3d
 *
 * @param <T> type of timeable entity that should be stored in this collection
 */
public class TimeableEntityCollection<T extends TimeableEntity> extends CopyOnWriteArrayList<T> {

    /**
     * Constructs a collection of timeable entities and populates it
     *
     * @param entities entities to populate this collection with initially
     */
    @SafeVarargs
    public TimeableEntityCollection(final T... entities) {
        addAll(Arrays.asList(entities));
    }

    /**
     * Sorts the collection with a comparator that should return the best option to gain resources from
     * @return a collection sorted in a way that the best option to gain resources from is first
     */
    public synchronized TimeableEntityCollection<T> sort() {
        final Player local;
        final Coordinate pos;
        if ((local = Players.getLocal()) != null && (pos = local.getPosition()) != null) {
            final HashMap<T, PropertyCache> cache = new HashMap<>(size());
            final ArrayList<T> sortingList = new ArrayList<>(this);

            sortingList.forEach(s -> cache.put(s, new PropertyCache(s.isAvailable(), s.getAvailabilityAge(), s.distanceTo(pos))));

            sortingList.sort(((o1, o2) -> {
                final boolean a1 = cache.get(o1).available, a2 = cache.get(o2).available;
                if (!a1 && !a2) {
                    final long t1 = cache.get(o1).availabilityAge, t2 = cache.get(o2).availabilityAge;
                    return t1 > t2 ? -1 : t1 == t2 ? 0 : 1;
                } else if (a1 && !a2) {
                    return -1;
                } else if (!a1) {
                    return 1;
                } else {
                    final double d1 = cache.get(o1).distance, d2 = cache.get(o2).distance;
                    return d1 < d2 ? -1 : d1 == d2 ? 0 : 1;
                }
            }));

            clear();
            addAll(sortingList);
        }

        return this;
    }

    public TimeableEntityCollection<T> observeEntities() {
        forEach(TimeableEntity::retrieveAvailability);
        return this;
    }

    /**
     * @return the positions of the entities in this collection
     */
    public Set<Coordinate> getCoordinates() {
        return stream().map(T::getPosition).collect(Collectors.toSet());
    }

    /**
     * @return the first entity in this list
     */
    public T first() {
        return size() > 0 ? get(0) : null;
    }

    private final static class PropertyCache {
        private final boolean available;
        private final long availabilityAge;
        private final double distance;

        private PropertyCache(boolean available, long availabilityAge, double distance) {
            this.available = available;
            this.availabilityAge = availabilityAge;
            this.distance = distance;
        }
    }
}
