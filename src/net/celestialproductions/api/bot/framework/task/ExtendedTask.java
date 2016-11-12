package net.celestialproductions.api.bot.framework.task;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.task.Task;
import com.runemate.game.api.script.framework.task.TaskBot;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.framework.accessors.AntipatternAccessor;
import net.celestialproductions.api.bot.framework.accessors.BreakschedulerAccessor;
import net.celestialproductions.api.bot.framework.accessors.PlayerAccessor;
import net.celestialproductions.api.bot.framework.accessors.BotAccessor;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author Savior
 */
public class ExtendedTask<T extends TaskBot & Mainclass<T>> extends Task implements PlayerAccessor, BotAccessor<T>, AntipatternAccessor, BreakschedulerAccessor {
    private final Callable<Boolean> validate;
    private final Runnable execute;
    private Antipattern.List antipatterns;
    private Player local;
    private T bot;

    public ExtendedTask(final Task... children) {
        this(null, null, children);
    }

    public ExtendedTask(final Callable<Boolean> validate, final Task... children) {
        this(validate, null, children);
    }

    public ExtendedTask(final Runnable execute, final Task... children) {
        this(null, execute, children);
    }

    public ExtendedTask(final Callable<Boolean> validate, final Runnable execute, final Task... children) {
        super(children);
        this.validate = validate;
        this.execute = execute;
    }

    @Override
    public boolean validate() {
        if (validate != null) {
            try {
                return validate.call();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
        if (execute != null) {
            execute.run();
        }
    }

    @Override
    public final Player local() {
        if (local == null || !local.isValid())
            local = Players.getLocal();
        return local;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final T bot() {
        if (bot == null)
            bot = (T) Environment.getBot();
        return bot;
    }

    @Override
    public final Antipattern antipattern() {
        if (antipatterns != null) {
            return antipatterns.random();
        }
        return bot().getAntipatterns().random();
    }

    @Override
    public final BreakScheduler breakScheduler() {
        return bot().breakScheduler();
    }

    public final void setAntipatterns(final Collection<Antipattern> antipatterns) {
        this.antipatterns = new Antipattern.List(antipatterns);
    }
}