package net.celestialproductions.api.game.antipattern;

import com.runemate.game.api.hybrid.entities.details.Onymous;
import com.runemate.game.api.hybrid.util.calculations.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Defeat3d
 */
public abstract class Antipattern implements Runnable, Onymous {


    public final boolean consider() {
        return false;
    }

    public final boolean force() {
        return false;
    }

    public static List defaultList() {
        return new List();
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
