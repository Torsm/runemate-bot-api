package com.productions.celestial.bots.firemaker.banking;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by sunny on 9/26/2016.
 */
public class GetLogs extends Task {

    private static final String ELDER_LOGS = "Elder logs";

    @Override
    public void execute() {
        System.out.println("Executing Get Items");
        final Npc npc = Npcs.newQuery().actions("Bank").names("Banker").results().nearest();
        if(!Bank.isOpen() && npc != null) {
            if(npc.click()) {
                Execution.delayUntil(Bank::isOpen, 2400, 3000);
            }
            return;
        }
        if(Bank.isOpen()) {
            if(!Bank.containsAllOf(ELDER_LOGS)) {
                Environment.getScript().stop();
            }
            System.out.println("Loading the preset.");
            if(Bank.loadPreset(1, true)) {
                Execution.delayUntil(() -> !Bank.isOpen() && Inventory.contains(ELDER_LOGS), 1500, 2000);
            }
        }
    }

    @Override
    public boolean validate() {
        return !Inventory.contains(ELDER_LOGS);
    }
}
