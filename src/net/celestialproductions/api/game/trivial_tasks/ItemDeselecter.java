package net.celestialproductions.api.game.trivial_tasks;

import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.framework.task.Task;

/**
 * @author Savior
 */
public class ItemDeselecter extends Task {
    private SpriteItem item;

    @Override
    public boolean validate() {
        return (item = Inventory.getSelectedItem()) != null;
    }

    @Override
    public void execute() {
        if (InterfaceWindows.getInventory().isOpen()) {
            item.click();
        } else {
            InterfaceWindows.getInventory().open();
        }
    }
}
