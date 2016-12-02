package net.celestialproductions.api.game.antipattern.implementations;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.Execution;
import net.celestialproductions.api.bot.settings.ChanceSetting;
import net.celestialproductions.api.bot.settings.Setting;
import net.celestialproductions.api.bot.settings.UserSettingsAccessor;
import net.celestialproductions.api.game.GameCalculations;
import net.celestialproductions.api.game.antipattern.Antipattern;

/**
 * @author Defeat3d
 */
public class WheelCamera extends Antipattern {
    private final static Setting<Integer> WEIGHT_SETTING = new Setting<>(UserSettingsAccessor.SHARED_SETTINGS, "wheelCameraWeight", Setting.INTEGER_STRING_CONVERTER);
    private final ChanceSetting directionChance = new ChanceSetting("cameraDirection", 0.2, 0.8);

    public WheelCamera() {
        this(WEIGHT_SETTING.get(Random.nextInt(10, 50)));
    }

    public WheelCamera(final int weight) {
        super("Turning camera using mouse wheel", weight);
    }

    @Override
    public void run() {
        final Player local = Players.getLocal();
        final boolean direction = directionChance.poll();

        if (local != null) {
            for (int i = 0; i < Random.nextInt(1, 5); i++) {

                final InteractablePoint pos;
                if (direction) {
                    pos = GameCalculations.deviatedMousePosition(-200, -50, -25, 25);
                } else {
                    pos = GameCalculations.deviatedMousePosition(50, 200, -25, 25);
                }

                local.hover();
                if (Mouse.press(Mouse.Button.WHEEL))
                    Mouse.move(pos);
                Execution.delay(100, 400);
                Mouse.release(Mouse.Button.WHEEL);
            }
        }
    }
}
