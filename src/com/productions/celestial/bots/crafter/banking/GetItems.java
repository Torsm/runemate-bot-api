package com.productions.celestial.bots.crafter.banking;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by Prabhdeep.Singh on 9/21/2016.
 */
public class GetItems extends Task {

    private static final String BATTLE_STAFF = "Battlestaff", AIR_ORB = "Air orb";

    @Override
    public void execute() {
        System.out.println("Executing Get Items");
        if(!Bank.isOpen() && Bank.open()) {
            Execution.delayUntil(Bank::isOpen, 2000, 2500);
            return;
        }
        if(Bank.isOpen()) {
            if(!Bank.containsAllOf(BATTLE_STAFF, AIR_ORB)) {
                Environment.getScript().stop();
            }
            System.out.println("Loading the preset.");
            if(Bank.loadPreset(1, true)) {
                Execution.delayUntil(() -> !Bank.isOpen() && Inventory.containsAllOf(BATTLE_STAFF, AIR_ORB), 1500, 2000);
            }
        }
    }

    @Override
    public boolean validate() {
        return !Inventory.containsAllOf(BATTLE_STAFF, AIR_ORB) && InterfaceWindows.getInventory().isOpen();
    }
}
