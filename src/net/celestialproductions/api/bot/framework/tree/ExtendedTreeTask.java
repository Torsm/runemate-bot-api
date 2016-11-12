package net.celestialproductions.api.bot.framework.tree;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.celestialproductions.api.bot.framework.accessors.AntipatternAccessor;
import net.celestialproductions.api.bot.framework.accessors.BotAccessor;
import net.celestialproductions.api.bot.framework.accessors.BreakschedulerAccessor;
import net.celestialproductions.api.bot.framework.accessors.PlayerAccessor;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author Savior
 */
public class ExtendedTreeTask<T extends TreeBot & Mainclass<T>> extends TreeTask implements PlayerAccessor, BotAccessor<T>, AntipatternAccessor, BreakschedulerAccessor {
    private TreeTask failureTask;
    private TreeTask successTask;
    private Callable<Boolean> validate;
    private Antipattern.List antipatterns;
    private Player local;
    private T bot;

    public ExtendedTreeTask() {
        this(null, null, null);
    }

    public ExtendedTreeTask(final Callable<Boolean> validate) {
        this(null, null, validate);
    }

    public ExtendedTreeTask(final TreeTask failureTask, final TreeTask successTask) {
        this(failureTask, successTask, null);
    }

    public ExtendedTreeTask(final TreeTask failureTask, final TreeTask successTask, final Callable<Boolean> validate) {
        this.failureTask = failureTask;
        this.successTask = successTask;
        this.validate = validate;
    }

    @Override
    public boolean isLeaf() {
        return failureTask == null && successTask == null;
    }

    @Override
    public void execute() {

    }

    @Override
    public TreeTask failureTask() {
        return failureTask == null ? new ExtendedTreeBot.EmptyLeaf() : failureTask;
    }

    @Override
    public TreeTask successTask() {
        return successTask == null ? new ExtendedTreeBot.EmptyLeaf() : successTask;
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
