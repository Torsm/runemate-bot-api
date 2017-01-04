package net.celestialproductions.api.bot.framework.tree;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * @author Savior
 */
public class ExtendedBranchTask<T extends TreeBot & Mainclass<T>> extends BranchTask {
    private Callable<Boolean> validate;
    private TreeTask failureTask;
    private TreeTask successTask;
    private Antipattern.List antipatterns;
    private Player local;
    private T bot;

    public ExtendedBranchTask() {
        this(null, null, null);
    }

    public ExtendedBranchTask(final Callable<Boolean> validate) {
        this(validate, null, null);
    }

    public ExtendedBranchTask(final TreeTask failureTask, final TreeTask successTask) {
        this(null, failureTask, successTask);
    }

    public ExtendedBranchTask(final Callable<Boolean> validate, final TreeTask failureTask, final TreeTask successTask) {
        this.validate = validate;
        this.failureTask = failureTask;
        this.successTask = successTask;
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
    public TreeTask failureTask() {
        return failureTask == null ? new ExtendedTreeBot.EmptyLeaf() : failureTask;
    }

    @Override
    public TreeTask successTask() {
        return successTask == null ? new ExtendedTreeBot.EmptyLeaf() : successTask;
    }

    public void setFailureTask(final TreeTask failureTask) {
        this.failureTask = failureTask;
    }

    public void setSuccessTask(final TreeTask successTask) {
        this.successTask = successTask;
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

    public final void setAntipatterns(final Antipattern... antipatterns) {
        setAntipatterns(Arrays.asList(antipatterns));
    }

    public final void setAntipatterns(final Collection<Antipattern> antipatterns) {
        this.antipatterns = new Antipattern.List(antipatterns);
    }
}
