package net.celestialproductions.api.util;

import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.entities.definitions.NpcDefinition;

/**
 * @author Savior
 */
public class Definitions {

    public static String nameOf(final ItemDefinition definition) {
        final String name = definition == null ? null : definition.getName();
        return name == null ? "" : name;
    }

    public static String nameOf(final NpcDefinition definition) {
        final String name = definition == null ? null : definition.getName();
        return name == null ? "" : name;
    }

    public static String nameOf(final GameObjectDefinition definition) {
        final String name = definition == null ? null : definition.getName();
        return name == null ? "" : name;
    }

}
