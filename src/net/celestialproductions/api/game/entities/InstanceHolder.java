package net.celestialproductions.api.game.entities;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.queries.LocatableEntityQueryBuilder;

/**
 * @author Defeat3d
 */
public interface InstanceHolder<T extends LocatableEntity, B extends LocatableEntityQueryBuilder<T, B>> {

    T instance();

    B builder();

}
