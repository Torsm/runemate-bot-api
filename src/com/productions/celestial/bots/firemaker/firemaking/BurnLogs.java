package com.productions.celestial.bots.firemaker.firemaking;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.local.hud.Menu;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

/**
 * Created by sunny on 9/26/2016.
 */
public class BurnLogs extends Task {

    private static final String ELDER_LOGS = "Elder logs", PORTABLE_BRAZIER = "Portable brazier",
            ADD_LOGS = "Add logs", FIRE = "Fire", USE = "USE", LIGHT = "Light";
    private GameObject fireObject;

    @Override
    public void execute() {
        System.out.println("Finding a fire or a portable brazier");
        final Player player = Players.getLocal();
        if(fireObject.click()) {
            Execution.delayUntil(() -> Menu.isOpen() ||
                    Execution.delayUntil(() -> player != null && player.getAnimationId() != -1, 2400, 3000));
            if(Menu.isOpen()) {
                if(Menu.close()) {
                    Execution.delayUntil(() -> !Menu.isOpen(), 1200, 1800);
                }
                return;
            }
            Execution.delayUntil(() -> {
                GameObject object = fireObject;
                return !Inventory.contains(ELDER_LOGS) || (object == null || !object.isValid());
            });
        }
    }

    @Override
    public boolean validate() {
        fireObject = GameObjects.newQuery().actions(ADD_LOGS).names(PORTABLE_BRAZIER).results().nearest();
        return !Bank.isOpen() && Inventory.contains(ELDER_LOGS) && fireObject != null;
    }
}
