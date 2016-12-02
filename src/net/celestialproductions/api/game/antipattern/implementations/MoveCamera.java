package net.celestialproductions.api.game.antipattern.implementations;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.util.calculations.Random;
import net.celestialproductions.api.bot.settings.ChanceSetting;
import net.celestialproductions.api.bot.settings.Setting;
import net.celestialproductions.api.bot.settings.UserSettingsAccessor;
import net.celestialproductions.api.game.GameCalculations;
import net.celestialproductions.api.game.antipattern.Antipattern;

/**
 * @author Savior
 */
public class MoveCamera extends Antipattern {
    private final static Setting<Integer> WEIGHT_SETTING = new Setting<>(UserSettingsAccessor.SHARED_SETTINGS, "moveCameraWeight", Setting.INTEGER_STRING_CONVERTER);
    private final ChanceSetting changePitchChance = new ChanceSetting("changePitchChance", 0.2, 0.8);

    public MoveCamera() {
        this(WEIGHT_SETTING.get(Random.nextInt(10, 50)));
    }

    public MoveCamera(final int weight) {
        super("Turning camera", weight);
    }

    @Override
    protected void run() {
        final int yaw = GameCalculations.deviatedCameraAngle(-180, 180);
        if (changePitchChance.poll()) {
            Camera.turnTo(yaw, Environment.isOSRS() ? Random.nextDouble(0.8, 1.0) : Random.nextDouble(0.35, 0.5));
        } else {
            Camera.turnTo(yaw);
        }
    }
}
