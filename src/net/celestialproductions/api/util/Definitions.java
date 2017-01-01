package net.celestialproductions.api.util;

import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.entities.definitions.NpcDefinition;

/**
 * @author Savior
 */
public final class Definitions {

    public static String getName(final ItemDefinition definition) {
        final String name = definition == null ? null : definition.getName();
        return name == null ? "" : name;
    }

    public static String getName(final NpcDefinition definition) {
        final String name = definition == null ? null : definition.getName();
        return name == null ? "" : name;
    }

    public static String getName(final GameObjectDefinition definition) {
        final String name = definition == null ? null : definition.getName();
        return name == null ? "" : name;
    }

}
