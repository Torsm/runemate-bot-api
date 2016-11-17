package net.celestialproductions.api.game.trivial_tasks;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.RuneScape;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.script.framework.task.Task;

/**
 * @author Savior
 */
public class SetCamera extends Task {

    @Override
    public void execute() {
        Camera.turnTo(Environment.isOSRS() ? 1.00 : 0.8);
    }

    @Override
    public boolean validate() {
        return RuneScape.isLoggedIn() && Camera.getPitch() < (Environment.isOSRS() ? 0.8 : 0.38);
    }
}
