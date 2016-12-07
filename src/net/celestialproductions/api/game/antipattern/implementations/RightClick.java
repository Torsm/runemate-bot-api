package net.celestialproductions.api.game.antipattern.implementations;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import net.celestialproductions.api.bot.settings.Setting;
import net.celestialproductions.api.bot.settings.UserSettingsAccessor;
import net.celestialproductions.api.game.GameCalculations;
import net.celestialproductions.api.game.antipattern.Antipattern;

/**
 * @author Torben
 */
public class RightClick extends Antipattern {
    private final static Setting<Integer> WEIGHT_SETTING = new Setting<>(UserSettingsAccessor.SHARED_SETTINGS, "rightClickWeight", Setting.INTEGER_STRING_CONVERTER);

    public RightClick() {
        this(WEIGHT_SETTING.get(Random.nextInt(10, 50)));
    }

    public RightClick(final int weight) {
        super("Right clicking", weight);
    }

    @Override
    protected void run() {
        if (Mouse.click(Mouse.Button.RIGHT)) {
            final InteractablePoint currentPosition = GameCalculations.deviatedMousePosition(-50, 50, 20, 60);

            Mouse.move(GameCalculations.deviatedMousePosition(-30, 30, -40, -20));
            Execution.delay(50, 350);
            Mouse.move(currentPosition);
        }
    }
}
