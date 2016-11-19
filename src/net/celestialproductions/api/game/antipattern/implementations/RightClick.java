package net.celestialproductions.api.game.antipattern.implementations;

import com.runemate.game.api.hybrid.input.Mouse;
import com.runemate.game.api.hybrid.local.hud.InteractablePoint;
import net.celestialproductions.api.game.GameCalculations;
import net.celestialproductions.api.game.antipattern.Antipattern;

/**
 * @author Torben
 */
public class RightClick extends Antipattern {

    @Override
    protected void run() {
        Mouse.click(Mouse.Button.RIGHT);
        final InteractablePoint pos = GameCalculations.deviatedMousePosition(-50, 50, -70, -30);
        if (GameCalculations.deviatedMousePosition(-30, 30, 20, 50).hover()) {
            pos.hover();
        }
    }

    @Override
    public String getName() {
        return "Right clicking";
    }
}
