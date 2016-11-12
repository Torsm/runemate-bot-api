package net.celestialproductions.api.game.antipattern;

import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.hybrid.util.Timer;
import com.runemate.game.api.hybrid.util.calculations.Random;
import com.runemate.game.api.script.framework.task.Task;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.framework.task.ExtendedTask;
import net.celestialproductions.api.bot.framework.task.ExtendedTaskBot;

import java.util.concurrent.TimeUnit;

/**
 * @author Savior
 */
public class IdleLimiter<T extends ExtendedTaskBot & Mainclass<T>> extends ExtendedTask<T> {
    private final StopWatch watch = new StopWatch();
    private int nextRuntime = Random.nextInt(40, 200);

    @Override
    public boolean validate() {
        if (!watch.isRunning())
            watch.start();
        return watch.getRuntime(TimeUnit.SECONDS) > nextRuntime;
    }

    @Override
    public void execute() {
        antipattern().force();
        nextRuntime = Random.nextInt(40, 200);
        watch.reset();
    }
}
