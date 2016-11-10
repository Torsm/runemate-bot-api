package net.celestialproductions.api.bot.framework;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.AbstractBot;

import java.util.concurrent.TimeUnit;

/**
 * Alternative class to LoopingThread. This implementation does not bind to the status of the bot, it continues running
 * even if the bot is paused.
 *
 * @author Savior
 */
public class IndependentLoopingThread extends Thread {
    private final AbstractBot bot;
    private final Runnable runnable;
    private final long frequency;

    public IndependentLoopingThread(final String name, final Runnable runnable, final long frequency) {
        this(Environment.getBot(), name, runnable, frequency, TimeUnit.MILLISECONDS);
    }

    public IndependentLoopingThread(final String name, final Runnable runnable, final long frequency, final TimeUnit timeUnit) {
        this(Environment.getBot(), name, runnable, frequency, timeUnit);
    }

    public IndependentLoopingThread(final AbstractBot bot, final String name, final Runnable runnable, final long frequency) {
        this(bot, name, runnable, frequency, TimeUnit.MILLISECONDS);
    }

    public IndependentLoopingThread(final AbstractBot bot, final String name, final Runnable runnable, final long frequency, final TimeUnit timeUnit) {
        super(name);
        this.bot = bot;
        this.runnable = runnable;
        this.frequency = timeUnit.toMillis(frequency);
    }

    @Override
    public void run() {
        while (!interrupted() && bot.getState() != AbstractBot.State.STOPPED) {
            runnable.run();
            Execution.delay(frequency);
        }
    }
}