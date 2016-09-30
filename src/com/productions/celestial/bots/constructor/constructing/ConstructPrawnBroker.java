package com.productions.celestial.bots.constructor.constructing;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by sunny on 9/23/2016.
 */
public class ConstructPrawnBroker extends Task {

    private static final String PRAWNBROKER_HOTSPOT = "Prawnbroker hotspot", BUILD = "Build",
            FLOTSAM_PRAWNBROKER = "Flotsam prawnbroker", REMOVE = "Remove";
    private GameObject prawnBrokeObject;

    @Override
    public void execute() {
        InterfaceComponent interfaceComponent = Interfaces.getAt(1306, 14, 2);
        if((interfaceComponent == null || !interfaceComponent.isVisible()) &&
                prawnBrokeObject != null && prawnBrokeObject.isVisible()) {
            System.out.println("Interacting with the hotspot");
            if(prawnBrokeObject.interact(BUILD, PRAWNBROKER_HOTSPOT)) {
                Execution.delayUntil(Menu::isOpen, 1800, 2400);
                if(Menu.isOpen() && !Menu.contains(BUILD, PRAWNBROKER_HOTSPOT)) {
                    if(Menu.close()) {
                        Execution.delayUntil(() -> !Menu.isOpen(), 1200, 1800);
                    }
                    return;
                }
                System.out.println("Interacting with the hotspot, waiting for the interface");
                Execution.delayUntil(() -> {
                    InterfaceComponent component1 =
                            Interfaces.getAt(1306, 14, 2);
                    return component1 != null && component1.isVisible();
                }, 3000, 3600);
            }
            return;
        }
        final InterfaceComponent interfaceComponent2 = Interfaces.getAt(1306, 14, 2);
        if(interfaceComponent2 != null && interfaceComponent2.isVisible()) {
            System.out.println("Interacting with the Flotsam prawnbrokers options");
            if(Execution.delayUntil(interfaceComponent2::click, 1200, 1800)) {
                Execution.delayUntil(() -> {
                    GameObject object = GameObjects.newQuery().actions(REMOVE).names(FLOTSAM_PRAWNBROKER).results().nearest();
                    return object != null && object.isVisible();
                }, 4800, 5400);
            }
        }
    }

    @Override
    public boolean validate() {
        prawnBrokeObject = GameObjects.newQuery().actions(BUILD).names(PRAWNBROKER_HOTSPOT).results().nearest();
        return prawnBrokeObject != null && prawnBrokeObject.isVisible() && Inventory.getQuantity(8782) >= 16;
    }
}
