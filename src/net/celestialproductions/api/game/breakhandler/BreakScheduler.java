package net.celestialproductions.api.game.breakhandler;

import com.runemate.game.api.hybrid.util.Timer;
import com.runemate.game.api.script.framework.AbstractBot;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.elements.Tab;

/**
 * @author Savior
 */
public final class BreakScheduler<T extends AbstractBot & Mainclass<T>> {
    private final T bot;
    private Timer timer;
    private Break nextBreak;
    private int minTimeBetweenBreaks;

    public BreakScheduler(final T bot) {
        this.bot = bot;
    }

    public void initialize() {
        final BreakConfigurator configurator = new BreakConfigurator(bot, this);
        final Tab tab = new Tab("Break scheduler", configurator, Tab.Priority.HIGHEST);
        bot.spectreUI().add(tab);
        reset();
    }

    public void reset() {
        timer = new Timer(minTimeBetweenBreaks);
        timer.start();
    }

    public boolean consider() {
        if (!timer.isRunning()) {
            //TODO do something
        }
        return false;
    }

    public boolean force() {
        if (!timer.isRunning()) {
            //TODO do something
        }
        return false;
    }

    public void setMinTimeBetweenBreaks(int minTimeBetweenBreaks) {
        this.minTimeBetweenBreaks = minTimeBetweenBreaks;
    }
}
