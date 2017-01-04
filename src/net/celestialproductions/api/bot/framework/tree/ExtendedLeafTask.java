package net.celestialproductions.api.bot.framework.tree;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.runemate.game.api.script.framework.tree.TreeBot;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Savior
 */
public class ExtendedLeafTask<T extends TreeBot & Mainclass<T>> extends LeafTask {
    private final Runnable runnable;
    private Antipattern.List antipatterns;
    private Player local;
    private T bot;

    public ExtendedLeafTask() {
        this(null);
    }

    public ExtendedLeafTask(final Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void execute() {
        if (runnable != null) {
            runnable.run();
        }
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
