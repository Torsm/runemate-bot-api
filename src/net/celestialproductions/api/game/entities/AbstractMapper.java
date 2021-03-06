package net.celestialproductions.api.game.entities;

import com.runemate.game.api.script.framework.AbstractBot;

import java.util.Collection;

/**
 * Abstract version of a mapper that can be used to attach timeable entities into a collection
 *
 * @author Defeat3d
 * @param <T> type of timeable entity to map (trees, spots, rocks)
 */
public abstract class AbstractMapper<T extends TimeableEntity<?, ?>> implements Runnable {
    private final AbstractBot bot;
    private final TimeableEntityCollection<?, T> collection;

    /**
     * Constructs a mapper
     * @param collection the collection to populate using this mapper
     */
    public AbstractMapper(final AbstractBot bot, final TimeableEntityCollection<?, T> collection) {
        this.bot = bot;
        this.collection = collection;
    }

    @Override
    public void run() {
        bot.getPlatform().invokeLater(() -> map().stream().filter(e -> !collection.contains(e)).forEach(collection::add));
    }

    /**
     * Abstract method that loads entities to potentially be added to the collection
     * @return a collection of entities to potentially add to the collection
     */
    public abstract Collection<T> map();

    /**
     * Manually map a single entity
     * @param entity Entity to be mapped
     */
    public void map(final T entity) {
        if (!collection.contains(entity))
            collection.add(entity);
    }
}
