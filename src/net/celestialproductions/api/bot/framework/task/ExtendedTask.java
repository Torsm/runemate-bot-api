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

/**
 * @author Savior
 */
public abstract class ExtendedTask<B extends TaskBot> extends Task implements PlayerAccessor, BotAccessor<B>, AntipatternAccessor, BreakschedulerAccessor {
    private Antipattern.List antipatterns;
    private Player local;
    private B bot;

    @Override
    public final Player local() {
        if (local == null || !local.isValid())
            local = Players.getLocal();
        return local;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final B bot() {
        if (bot == null)
            bot = (B) Environment.getBot();
        return bot;
    }

    @Override
    public final Antipattern antipattern() {
        if (antipatterns != null) {
            return antipatterns.random();
        } else if (bot instanceof Mainclass) {
            final Mainclass botExtender = (Mainclass) bot;
            return botExtender.getAntipatterns().random();
        }
        return null;
    }

    @Override
    public BreakScheduler breakScheduler() {
        if (bot instanceof Mainclass) {
            final Mainclass botExtender = (Mainclass) bot;
            return botExtender.breakScheduler();
        }
        return null;
    }

    public void setAntipatterns(final Collection<Antipattern> antipatterns) {
        this.antipatterns = new Antipattern.List(antipatterns);
    }
}
