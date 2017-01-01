package net.celestialproductions.api.bot.spectreui.elements.profitpane.listeners;

import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import net.celestialproductions.api.bot.spectreui.elements.profitpane.ProfitPane;

/**
 * @author Savior
 */
public class DedicatedInventoryListener implements InventoryListener {
    private final ProfitPane profitPane;

    public DedicatedInventoryListener(final ProfitPane profitPane) {
        this.profitPane = profitPane;
    }

    @Override
    public void onItemAdded(final ItemEvent itemEvent) {

    }

    @Override
    public void onItemRemoved(final ItemEvent itemEvent) {

    }
}
