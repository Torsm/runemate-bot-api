package net.celestialproductions.api.bot.queries.filters;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.definitions.GameObjectDefinition;
import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.entities.definitions.NpcDefinition;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.util.Regex;
import com.runemate.game.api.rs3.local.hud.interfaces.eoc.ActionBar;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Savior
 */
public abstract class ExcludedNameFilter<T> implements Predicate<T> {

    public static ExcludedNameFilter<SpriteItem> spriteItem(final String... names) {
        return ExcludedNameFilter.spriteItem(Regex.getPatternsForExactStrings(names));
    }

    public static ExcludedNameFilter<SpriteItem> spriteItem(final Pattern... names) {
        return ExcludedNameFilter.spriteItem(Arrays.asList(names));
    }

    public static ExcludedNameFilter<SpriteItem> spriteItem(final Collection<Pattern> names) {
        return new ExcludedNameFilter<SpriteItem>() {
            @Override
            public boolean test(SpriteItem spriteItem) {
                final ItemDefinition def = spriteItem.getDefinition();
                final String name = def == null ? null : def.getName();
                if (name != null) {
                    for (Pattern pattern : names) {
                        if (pattern.matcher(name).matches()) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }

    public static ExcludedNameFilter<Npc> npc(final String... names) {
        return ExcludedNameFilter.npc(Regex.getPatternsForExactStrings(names));
    }

    public static ExcludedNameFilter<Npc> npc(final Pattern... names) {
        return ExcludedNameFilter.npc(Arrays.asList(names));
    }

    public static ExcludedNameFilter<Npc> npc(final Collection<Pattern> names) {
        return new ExcludedNameFilter<Npc>() {
            @Override
            public boolean test(Npc npc) {
                final NpcDefinition def = npc.getDefinition();
                final String name = def == null ? null : def.getName();
                if (name != null) {
                    for (Pattern pattern : names) {
                        if (pattern.matcher(name).matches()) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }

    public static ExcludedNameFilter<GameObject> gameObject(final String... names) {
        return ExcludedNameFilter.gameObject(Regex.getPatternsForExactStrings(names));
    }

    public static ExcludedNameFilter<GameObject> gameObject(final Pattern... names) {
        return ExcludedNameFilter.gameObject(Arrays.asList(names));
    }

    public static ExcludedNameFilter<GameObject> gameObject(final Collection<Pattern> names) {
        return new ExcludedNameFilter<GameObject>() {
            @Override
            public boolean test(GameObject gameObject) {
                final GameObjectDefinition def = gameObject.getDefinition();
                final String name = def == null ? null : def.getName();
                if (name != null) {
                    for (Pattern pattern : names) {
                        if (pattern.matcher(name).matches()) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }

    public static ExcludedNameFilter<ActionBar.Slot> slot(final String... names) {
        return ExcludedNameFilter.slot(Regex.getPatternsForExactStrings(names));
    }

    public static ExcludedNameFilter<ActionBar.Slot> slot(final Pattern... names) {
        return ExcludedNameFilter.slot(Arrays.asList(names));
    }

    public static ExcludedNameFilter<ActionBar.Slot> slot(final Collection<Pattern> names) {
        return new ExcludedNameFilter<ActionBar.Slot>() {
            @Override
            public boolean test(ActionBar.Slot slot) {
                final String name = slot.getName();
                if (name != null) {
                    for (Pattern pattern : names) {
                        if (pattern.matcher(name).matches()) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };
    }
}
