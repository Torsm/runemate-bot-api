package net.celestialproductions.api.game.antipattern;

import com.runemate.game.api.hybrid.entities.details.Onymous;
import com.runemate.game.api.hybrid.util.calculations.Random;
import net.celestialproductions.api.bot.framework.extender.BotExtender;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.game.antipattern.implementations.MoveMouseSlightly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Defeat3d
 */
public abstract class Antipattern implements Onymous {

    public final boolean consider() {
        //TODO enhance chance to execute
        return Random.nextInt(0, 100) < 30 && execute();
    }

    public final boolean force() {
        return execute();
    }

    private boolean execute() {
        final Mainclass bot = BotExtender.instance();
        final String status = bot.getStatus();
        bot.setStatus("[Antipattern] " + getName());
        final boolean b = run();
        bot.setStatus(status);
        return b;
    }

    protected abstract boolean run();

    public static List defaultList() {
        //TODO implement more antipatterns
        return new List(new MoveMouseSlightly());
    }

    public static class List extends ArrayList<Antipattern> {
        public List(final Antipattern... antipatterns) {
            this(Arrays.asList(antipatterns));
        }

        public List(final Collection<Antipattern> antipatterns) {
            addAll(antipatterns);
        }

        public Antipattern random() {
            if (isEmpty())
                return null;
            return get(Random.nextInt(size()));
        }
    }
}
