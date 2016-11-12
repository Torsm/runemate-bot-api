package net.celestialproductions.api.game.trivial_tasks;

import com.runemate.game.api.hybrid.local.Varbits;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.TaskBot;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.framework.task.ExtendedTask;

/**
 * @author Savior
 */
public class TeleportUrn<T extends TaskBot & Mainclass<T>> extends ExtendedTask<T> {
    private SpriteItem urn;

    @Override
    public boolean validate() {
        return Varbits.load(0).getValue() == 0 && (urn = Inventory.newQuery().actions("Teleport urn").results().first()) != null;
    }

    @Override
    public void execute() {
        bot().setStatus("Teleporting Urn");
        if (InterfaceWindows.getInventory().isOpen()) {
            if (urn.interact("Teleport urn")) {
                antipattern().consider();
                Execution.delayWhile(urn::isValid, 1200, 1800);
            }
        } else {
            InterfaceWindows.getInventory().open();
        }
    }
}
