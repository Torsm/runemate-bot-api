package net.celestialproductions.api.game.items;

import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Savior
 */
public class ChargeComparator implements Comparator<SpriteItem> {
    private final static Pattern CHARGE_PATTERN = Pattern.compile("\\(\\d+\\)");
    private final Map<SpriteItem, Integer> cache = new HashMap<>();

    @Override
    public int compare(final SpriteItem s1, final SpriteItem s2) {
        return getCharge(s1) - getCharge(s2);
    }

    private Integer getCharge(final SpriteItem s) {
        Integer i = cache.get(s);

        if (i == null) {
            final ItemDefinition def = s.getDefinition();
            final String name = def == null ? null : def.getName();

            if (name != null) {
                final Matcher m = CHARGE_PATTERN.matcher(name);
                if (m.find()) {
                    final String result = m.group().replace("(", "").replace(")", "");
                    i = Integer.parseInt(result);
                    cache.put(s, i);
                }
            }
        }

        return i;
    }
}