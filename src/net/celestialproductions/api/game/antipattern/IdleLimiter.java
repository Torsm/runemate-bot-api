package net.celestialproductions.api.game.antipattern;

import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.calculations.Random;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.framework.task.ExtendedTask;
import net.celestialproductions.api.bot.framework.task.ExtendedTaskBot;
import net.celestialproductions.api.bot.settings.ChanceSetting;
import net.celestialproductions.api.game.antipattern.implementations.MoveCamera;
import net.celestialproductions.api.game.antipattern.implementations.RightClick;
import net.celestialproductions.api.game.antipattern.implementations.WheelCamera;

import java.util.concurrent.TimeUnit;

/**
 * @author Savior
 */
public class IdleLimiter<T extends ExtendedTaskBot & Mainclass<T>> extends ExtendedTask<T> {
    private final ChanceSetting afkChance = new ChanceSetting("afkChance", 0.2, 0.5);
    private final StopWatch watch = new StopWatch();
    private int nextRuntime;

    public IdleLimiter() {
        setAntipatterns(new RightClick(70), new WheelCamera(), new MoveCamera());
    }

    @Override
    public boolean validate() {
        if (!watch.isRunning())
            watch.start();
        return watch.getRuntime(TimeUnit.SECONDS) > nextRuntime;
    }

    @Override
    public void execute() {
        antipattern().force();
        nextRuntime = (int) Random.nextGaussian(20, 210, afkChance.poll() ? 200 : 30);
        watch.reset();
    }
}
