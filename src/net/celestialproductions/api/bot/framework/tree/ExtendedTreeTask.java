package net.celestialproductions.api.bot.framework.tree;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author Savior
 */
public class ExtendedTreeTask<T extends TreeBot & Mainclass<T>> extends TreeTask {
    private Callable<Boolean> validate;
    private TreeTask successTask;
    private TreeTask failureTask;
    private Antipattern.List antipatterns;
    private Player local;
    private T bot;

    public ExtendedTreeTask() {
        this(null, null, null);
    }

    public ExtendedTreeTask(final Callable<Boolean> validate) {
        this(validate, null, null);
    }

    public ExtendedTreeTask(final TreeTask successTask, final TreeTask failureTask) {
        this(null, successTask, failureTask);
    }

    public ExtendedTreeTask(final Callable<Boolean> validate, final TreeTask successTask, final TreeTask failureTask) {
        this.validate = validate;
        this.successTask = successTask;
        this.failureTask = failureTask;
    }

    @Override
    public boolean isLeaf() {
        return successTask() == null && failureTask() == null;
    }

    @Override
    public void execute() {

    }

    @Override
    public TreeTask successTask() {
        return successTask == null ? new ExtendedTreeBot.EmptyLeaf() : successTask;
    }

    @Override
    public TreeTask failureTask() {
        return failureTask == null ? new ExtendedTreeBot.EmptyLeaf() : failureTask;
    }

    public void setSuccessTask(final TreeTask successTask) {
        this.successTask = successTask;
    }

    public void setFailureTask(final TreeTask failureTask) {
        this.failureTask = failureTask;
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

    public final Player local() {
        if (local == null || !local.isValid())
            local = Players.getLocal();
        return local;
    }

    @SuppressWarnings("unchecked")
    public final T bot() {
        if (bot == null)
            bot = (T) Environment.getBot();
        return bot;
    }

    public final Antipattern antipattern() {
        if (antipatterns != null) {
            return antipatterns.random();
        }
        return bot().getAntipatterns().random();
    }

    public final BreakScheduler breakScheduler() {
        return bot().breakScheduler();
    }

    public final void setAntipatterns(final Collection<Antipattern> antipatterns) {
        this.antipatterns = new Antipattern.List(antipatterns);
    }
}
