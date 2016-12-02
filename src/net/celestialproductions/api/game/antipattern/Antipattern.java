package net.celestialproductions.api.game.antipattern;

import com.runemate.game.api.hybrid.util.calculations.Random;
import net.celestialproductions.api.bot.framework.extender.BotExtender;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.settings.ChanceSetting;
import net.celestialproductions.api.game.antipattern.implementations.MoveCamera;
import net.celestialproductions.api.game.antipattern.implementations.MoveMouseSlightly;
import net.celestialproductions.api.game.antipattern.implementations.RightClick;
import net.celestialproductions.api.game.antipattern.implementations.WheelCamera;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author Defeat3d
 */
public abstract class Antipattern {
    private final ChanceSetting executionChance = new ChanceSetting("antipatternChance", 0.4, 0.7);
    private final String name;
    private final int weight;

    public Antipattern(final String name, final int weight) {
        this.name = name;
        this.weight = weight;
    }

    public final void consider() {
        if (executionChance.poll())
            execute();
    }

    public final void force() {
        execute();
    }

    private void execute() {
        final Mainclass bot = BotExtender.instance();
        final String status = bot.getStatus();
        bot.setStatus("[Antipattern] " + name);
        run();
        bot.setStatus(status);
    }

    protected abstract void run();

    public int getWeight() {
        return weight;
    }

    public static List defaultList() {
        return new List(new MoveMouseSlightly(), new RightClick(), new WheelCamera(), new MoveCamera());
    }

    public static class List extends ArrayList<Antipattern> {
        public List(final Antipattern... antipatterns) {
            this(Arrays.asList(antipatterns));
        }

        public List(final Collection<Antipattern> antipatterns) {
            addAll(antipatterns);
        }

        public Antipattern random() {
            final int totalWeight = stream().mapToInt(Antipattern::getWeight).sum();
            int random = Random.nextInt(totalWeight);

            for (Antipattern antipattern : this) {
                if (random < antipattern.getWeight())
                    return antipattern;
                random -= antipattern.getWeight();
            }

            return null;
        }
    }
}
