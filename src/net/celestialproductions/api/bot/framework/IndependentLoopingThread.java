package net.celestialproductions.api.bot.framework;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.AbstractScript;
import com.runemate.game.api.script.framework.core.LoopingThread;

import java.util.concurrent.TimeUnit;

/**
 * Alternative class to LoopingThread. This implementation does not bind to the status of the bot, it continues running
 * even if the bot is paused.
 *
 * @author Savior
 */
public class IndependentLoopingThread extends Thread {
    private final Runnable runnable;
    private final long frequency;

    public IndependentLoopingThread(final String name, final Runnable runnable, final long frequency) {
        this(name, runnable, frequency, TimeUnit.MILLISECONDS);
    }

    public IndependentLoopingThread(final String name, final Runnable runnable, final long frequency, final TimeUnit timeUnit) {
        super(name);
        this.runnable = runnable;
        this.frequency = timeUnit.toMillis(frequency);
    }

    @Override
    public void run() {
        final AbstractBot bot = Environment.getBot();
        if (bot != null) {
            
        }






        while (!interrupted()) {
            final AbstractBot script = Environment.getBot();
            if (script == null || AbstractBot.State.STOPPED.equals(script.getState())) {
                return;
            } else {
                try {
                    runnable.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Execution.delay(frequency);
            }
        }
    }

    public void refresh() {
        runnable.run();
    }
}