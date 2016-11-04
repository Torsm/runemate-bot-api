package net.celestialproductions.api.game.antipattern.implementations;

import net.celestialproductions.api.game.GameCalculations;
import net.celestialproductions.api.game.antipattern.Antipattern;

/**
 * @author Savior
 */
public class MoveMouseSlightly extends Antipattern {

    @Override
    protected boolean run() {
        return GameCalculations.deviatedMousePosition(-120, 100, -70, 80).hover();
    }

    @Override
    public String getName() {
        return "Moving mouse slightly";
    }
}
