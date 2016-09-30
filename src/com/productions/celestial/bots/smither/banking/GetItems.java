package com.productions.celestial.bots.smither.banking;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.Camera;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by sunny on 9/26/2016.
 */
public class GetItems extends Task {

    private static final String GOLD_ORE = "Gold ore", BANK = "Bank", BANKER = "Banker",
            PORTABLE_FORGE = "Portable forge", SMELT = "Smelt";
    private Npc banker;
    private GameObject forge;

    @Override
    public void execute() {
        System.out.println("Grabbing items.");
        Camera.concurrentlyTurnTo(banker);
        if(!Bank.isOpen() && banker.interact(BANK, BANKER)) {
            Execution.delayUntil(() -> Menu.isOpen() ? Menu.close() && !Menu.isOpen() : Bank.isOpen(), 2400, 3000);
            return;
        }
        if(Bank.isOpen()) {
            if(!Inventory.contains(PORTABLE_FORGE) &&
                    (forge = GameObjects.newQuery().actions(SMELT).names(PORTABLE_FORGE).results().nearest()) == null) {
                System.out.println("No forge for us to smelt, grabbing one from the bank.");
                if(Bank.withdraw(PORTABLE_FORGE, 1)) {
                    Execution.delayUntil(() -> Inventory.contains(PORTABLE_FORGE), 2400, 3000);
                    return;
                }
            }
            if(Inventory.contains(PORTABLE_FORGE)) {
                System.out.println("Withdrawing the ores.");
                if(Bank.withdraw(GOLD_ORE, 0)) {
                    Execution.delayUntil(() -> Inventory.contains(GOLD_ORE), 2400, 3000);
                    if(Inventory.contains(GOLD_ORE)) {
                        if(Execution.delayUntil(Bank::close, 1800, 2400)) {
                            Execution.delayUntil(() -> !Bank.isOpen(), 2400, 3000);
                        }
                    }
                }
            } else {
                System.out.println("No need for a forge as one exists, grabbing the ores.");
                if(Bank.loadPreset(1, true)) {
                    Execution.delayUntil(() -> Inventory.contains(GOLD_ORE) && !Bank.isOpen(), 2400, 3000);
                }
            }
        }
    }

    @Override
    public boolean validate() {
        forge = GameObjects.newQuery().actions(SMELT).names(PORTABLE_FORGE).results().nearest();
        banker = Npcs.newQuery().actions(BANK).names(BANKER).results().nearest();
        return (!Inventory.contains(GOLD_ORE) || forge == null) && banker != null;
    }
}
