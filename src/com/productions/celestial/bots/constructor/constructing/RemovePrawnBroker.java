package com.productions.celestial.bots.constructor.constructing;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

import java.awt.event.KeyEvent;

/**
 * Created by sunny on 9/24/2016.
 */
public class RemovePrawnBroker extends Task {

    private static final String FLOTSAM_PRAWNBROKER = "Flotsam prawnbroker", REMOVE = "Remove",
            PRAWNBROKER_HOTSPOT = "Prawnbroker hotspot", BUILD = "Build";
    private GameObject prawnBrokeObject;

    @Override
    public void execute() {
        System.out.println("Interacting with the prawnbroker");
        if(ChatDialog.getOptions().size() == 0 && prawnBrokeObject.interact(REMOVE, FLOTSAM_PRAWNBROKER)) {
            Execution.delayUntil(Menu::isOpen, 1800, 2400);
            if(Menu.isOpen() && !Menu.contains(REMOVE, FLOTSAM_PRAWNBROKER)) {
                if(Menu.close()) {
                    Execution.delayUntil(() -> !Menu.isOpen(), 1200, 1800);
                }
                return;
            }
            Execution.delayUntil(() -> ChatDialog.getOptions().size() > 0, 3000, 3600);
            return;
        }
        if(ChatDialog.getOptions().size() > 0) {
            if(Execution.delayUntil(() -> Keyboard.typeKey(KeyEvent.VK_1), 1200, 1800)) {
                System.out.println("Removing the prawnbroker");
                Execution.delayUntil(() -> {
                    GameObject gameObject = GameObjects.newQuery().actions(BUILD).names(PRAWNBROKER_HOTSPOT)
                            .results().nearest();
                    return gameObject != null && gameObject.isVisible();
                }, 4800, 5400);
            }
        }
    }

    @Override
    public boolean validate() {
        prawnBrokeObject = GameObjects.newQuery().actions(REMOVE).names(FLOTSAM_PRAWNBROKER).results().nearest();
        return prawnBrokeObject != null && prawnBrokeObject.isVisible() &&
                Inventory.getQuantity(8782) >= 16;
    }
}
