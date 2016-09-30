package com.productions.celestial.bots.constructor.banking;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.House;
import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

import java.awt.event.KeyEvent;

/**
 * Created by sunny on 9/21/2016.
 */
public class GetServant extends Task {

    private static final String MAHOGANY_PLANK = "Mahogany plank", FETCH_FROM_BANK = "Fetch-from-bank",
            FLOTSAM_PRAWNBROKER = "Flotsam prawnbroker", REMOVE = "Remove", PRAWNBROKER_HOTSPOT = "Prawnbroker hotspot", BUILD = "Build";

    @Override
    public void execute() {
        System.out.println("Fetching the Servant");
        final Npc servant = House.getServant();
        if(ChatDialog.getOptions().isEmpty() && servant != null && servant.isVisible()) {
            if(servant.interact(FETCH_FROM_BANK, servant.getName())) {
                System.out.println("Interacted with the servant.");
                Execution.delayUntil(() -> ChatDialog.getOptions().size() > 0, 3000, 3600);
                return;
            }
        }
        if(ChatDialog.getOptions() != null && !ChatDialog.getOptions().isEmpty()) {
            if(Execution.delayUntil(() -> Keyboard.typeKey(KeyEvent.VK_1), 1200, 1800)) {
                System.out.println("Sent the servant to get planks;");
                Execution.delayUntil(() -> {
                    Execution.delayUntil(() -> {
                        Npc npc = House.getServant();
                        return npc == null || !npc.isVisible();
                    }, 2400, 3000);
                    if(Inventory.getQuantity(8782) >= 8) {
                        GameObject object = GameObjects.newQuery().actions(REMOVE).names(FLOTSAM_PRAWNBROKER).results().nearest();
                        if(object != null && object.isVisible() && ChatDialog.getOptions().size() == 0) {
                            System.out.println("Removing the prawnbroker while waiting for the servant.");
                            if(object.interact(REMOVE, FLOTSAM_PRAWNBROKER)) {
                                Execution.delayUntil(() -> ChatDialog.getOptions().size() > 0, 2400, 3000);
                            }
                        }
                        if(ChatDialog.getOptions().size() > 0) {
                            if(Execution.delayUntil(() -> Keyboard.typeKey(KeyEvent.VK_1), 1200, 1800)) {
                                Execution.delayUntil(() -> {
                                    GameObject gameObject = GameObjects.newQuery().actions(BUILD).names(PRAWNBROKER_HOTSPOT)
                                            .results().nearest();
                                    return gameObject != null && gameObject.isVisible();
                                }, 4800, 5400);
                            }
                        }
                        GameObject gameObject = GameObjects.newQuery().actions(BUILD).names(PRAWNBROKER_HOTSPOT)
                                .results().nearest();
                        if(gameObject != null && gameObject.isVisible()) {
                            System.out.println("Building the prawnbroker while waiting for the servant.");
                            if(gameObject.interact(BUILD, PRAWNBROKER_HOTSPOT)) {
                                Execution.delayUntil(() -> {
                                    InterfaceComponent component1 = Interfaces.getAt(1306, 14, 2);
                                    return component1 != null && component1.isVisible();
                                }, 3000, 3600);
                            }
                        }
                        InterfaceComponent component = Interfaces.getAt(1306, 14, 2);
                        if(component != null && component.isVisible()) {
                            Execution.delayUntil(component::click, 1200, 1800);
                            Execution.delayUntil(() -> {
                                GameObject object3 = GameObjects.newQuery().actions(REMOVE).
                                        names(FLOTSAM_PRAWNBROKER).results().nearest();
                                return object3 != null && object3.isVisible();
                            }, 4800, 5400);
                        }
                    }
                    Npc npc = House.getServant();
                    return npc != null && npc.isVisible();
                }, 6600, 7200);
            }
        }
    }

    @Override
    public boolean validate() {
        return !Inventory.contains(8782) || Inventory.getQuantity(8782) < 16;
    }
}
