package net.celestialproductions.api.bot.settings;

import com.runemate.game.api.hybrid.util.calculations.Random;
import net.celestialproductions.api.util.Chance;

/**
 * @author Savior
 */
public class ChanceSetting extends Setting<Double> implements Chance {
    private final double min;
    private final double max;

    public ChanceSetting(final String name, final double min, final double max) {
        super(UserSettingsAccessor.SHARED_SETTINGS, name, DOUBLE_STRING_CONVERTER);
        this.min = min;
        this.max = max;
    }

    @Override
    public double chance() {
        return get(Random.nextDouble(min, max));
    }
}
