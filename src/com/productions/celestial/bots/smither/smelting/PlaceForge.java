package com.productions.celestial.bots.smither.smelting;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.cognizant.RegionPath;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.framework.task.Task;

import java.awt.event.KeyEvent;

/**
 * Created by sunny on 9/26/2016.
 */
public class PlaceForge extends Task {

    private static final String SMELT = "Smelt", PORTABLE_FORGE = "Portable forge", GOLD_ORE = "Gold ore";
    private final Coordinate randomCoordinate;
    private final Area.Rectangular area;

    public PlaceForge(final Area.Rectangular area, final Coordinate randomCoordinate) {
        this.area = area;
        this.randomCoordinate = randomCoordinate;
    }

    @Override
    public void execute() {
        System.out.println("Getting a random coordinate to place the forge on.");
        if(!GameObjects.newQuery().on(randomCoordinate).results().isEmpty()) {
            System.out.println("Another object is on the coordinate, finding a new coordinate for the forge.");
            return;
        }
        final RegionPath path = RegionPath.buildTo(randomCoordinate);
        final Player player = Players.getLocal();
        if(player != null && !area.contains(player) && path != null) {
            System.out.println("Walking to the random coordinate to place the forge.");
            if(path.step() && Execution.delayUntil(() -> player.getAnimationId() != -1, 1800, 2400)) {
                Execution.delayUntil(() -> player.getAnimationId() == -1, 1200, 1800);
                return;
            }
        }
        if(player != null && area.contains(player)) {
            if(Execution.delayUntil(() -> Keyboard.typeKey(KeyEvent.VK_1), 1200, 1800)) {
                System.out.println("Placed the forge.");
                Execution.delayUntil(() -> !Inventory.contains(PORTABLE_FORGE), 1800, 2400);
            }
        }
    }

    @Override
    public boolean validate() {
        final GameObject forge = GameObjects.newQuery().actions(SMELT).names(PORTABLE_FORGE).results().nearest();
        return !Bank.isOpen() && Inventory.containsAllOf(PORTABLE_FORGE, GOLD_ORE) && forge == null;
    }
}
