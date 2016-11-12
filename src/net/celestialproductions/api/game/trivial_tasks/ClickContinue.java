package net.celestialproductions.api.game.trivial_tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.script.framework.task.Task;

/**
 * @author Savior
 */
public class ClickContinue extends Task {
    private ChatDialog.Continue option;

    @Override
    public boolean validate() {
        return (option = ChatDialog.getContinue()) != null;
    }

    @Override
    public void execute() {
        option.select();
    }
}
