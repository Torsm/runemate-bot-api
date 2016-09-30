package com.productions.celestial.bots.crafter.crafting;

import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.*;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

import java.awt.event.KeyEvent;

/**
 * Created by sunny on 9/21/2016.
 */
public class MakeBattlestaff extends Task {

    private static final String BATTLE_STAFF = "Battlestaff", AIR_ORB = "Air orb",
            ADD_TO_BATTLESTAFF = "Add to battlestaff";

    @Override
    public void execute() {
        System.out.println("Executing Make Battlestaff");
        final SpriteItem airOrb = Inventory.newQuery().names(AIR_ORB).results().first();
        InterfaceComponent interfaceComponent =
                Interfaces.newQuery().texts("Craft").grandchildren(false).containers(1370).results().first();
        if(airOrb != null && airOrb.isVisible() && (interfaceComponent == null || !interfaceComponent.isVisible())) {
            System.out.println("Interacting with the Air orb");
            if(Keyboard.typeKey(KeyEvent.VK_1)) {
                Execution.delayUntil(() -> {
                    InterfaceComponent component =
                            Interfaces.newQuery().texts("Craft").grandchildren(false).containers(1370).results().first();
                    return component != null && component.isVisible();
                });
            }
        }
        interfaceComponent = Interfaces.newQuery().texts("Craft").grandchildren(false).containers(1370).results().first();
        if(interfaceComponent != null && interfaceComponent.isVisible()) {
            if(Keyboard.typeKey(KeyEvent.VK_SPACE)) {
                System.out.println("Creating Air Battlestaffs");
                Execution.delayUntil(() -> !Inventory.containsAllOf(BATTLE_STAFF, AIR_ORB));
            }
        }
    }

    @Override
    public boolean validate() {
        return !Bank.isOpen() && Inventory.containsAllOf(BATTLE_STAFF, AIR_ORB) &&
                InterfaceWindows.getInventory().isOpen();
    }
}
