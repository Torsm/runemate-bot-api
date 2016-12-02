package net.celestialproductions.api.game.antipattern.implementations;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.util.calculations.Random;
import net.celestialproductions.api.bot.settings.Setting;
import net.celestialproductions.api.bot.settings.UserSettingsAccessor;
import net.celestialproductions.api.game.GameCalculations;
import net.celestialproductions.api.game.antipattern.Antipattern;

/**
 * @author Savior
 */
public class MoveMouseSlightly extends Antipattern {
    private final static Setting<Integer> WEIGHT_SETTING = new Setting<>(UserSettingsAccessor.SHARED_SETTINGS, "moveMouseSlightlyWeight", Setting.INTEGER_STRING_CONVERTER);

    public MoveMouseSlightly() {
        this(WEIGHT_SETTING.get(Random.nextInt(10, 50)));
    }

    public MoveMouseSlightly(final int weight) {
        super("Moving mouse slightly", weight);
    }

    @Override
    protected void run() {
        Mouse.move(GameCalculations.deviatedMousePosition(-120, 100, -70, 80));
    }
}
