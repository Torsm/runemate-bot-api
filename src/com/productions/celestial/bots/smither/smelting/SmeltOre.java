package com.productions.celestial.bots.smither.smelting;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceComponent;
import com.runemate.game.api.hybrid.local.hud.interfaces.Interfaces;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

import java.awt.event.KeyEvent;

/**
 * Created by sunny on 9/27/2016.
 */
public class SmeltOre extends Task {

    private static final String PORTABLE_FORGE = "Portable forge", GOLD_ORE = "Gold ore", SMELT = "Smelt";
    private final Coordinate randomCoordinate;
    private GameObject forge;

    public SmeltOre(final Coordinate randomCoordinate) {
        this.randomCoordinate = randomCoordinate;
    }

    @Override
    public void execute() {
        System.out.println("Interacting with the forge.");
        InterfaceComponent interfaceComponent =
                Interfaces.newQuery().texts(SMELT).grandchildren(false).containers(1370).results().first();
        if((interfaceComponent == null || !interfaceComponent.isVisible()) && forge.interact(SMELT, PORTABLE_FORGE)) {
            Execution.delayUntil(() -> {
                InterfaceComponent component =
                        Interfaces.newQuery().texts(SMELT).grandchildren(false).containers(1370).results().first();
                return component != null && component.isVisible();
            }, 1800, 2400);
            return;
        }
        interfaceComponent = Interfaces.newQuery().texts(SMELT).grandchildren(false).containers(1370).results().first();
        if(interfaceComponent != null && interfaceComponent.isVisible()) {
            System.out.println("Starting the smelting process");
            if(Keyboard.typeKey(KeyEvent.VK_SPACE)) {
                Execution.delayUntil(() -> !Inventory.contains(GOLD_ORE) || !forge.isValid());
            }
        }
    }

    @Override
    public boolean validate() {
        forge = GameObjects.newQuery().actions(SMELT).names(PORTABLE_FORGE).on(randomCoordinate).results().size() > 0 ?
                GameObjects.newQuery().actions(SMELT).names(PORTABLE_FORGE).on(randomCoordinate).results().first() :
                GameObjects.newQuery().actions(SMELT).names(PORTABLE_FORGE).results().first();
        return !Bank.isOpen() && Inventory.contains(GOLD_ORE) && !Inventory.contains(PORTABLE_FORGE) && forge != null;
    }
}
