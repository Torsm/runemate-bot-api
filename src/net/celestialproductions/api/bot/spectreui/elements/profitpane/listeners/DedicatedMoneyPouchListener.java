package net.celestialproductions.api.bot.spectreui.elements.profitpane.listeners;

import com.runemate.game.api.script.framework.listeners.MoneyPouchListener;
import com.runemate.game.api.script.framework.listeners.events.MoneyPouchEvent;
import net.celestialproductions.api.bot.spectreui.elements.profitpane.ProfitPane;

/**
 * @author Savior
 */
public class DedicatedMoneyPouchListener implements MoneyPouchListener {
    private final ProfitPane profitPane;

    public DedicatedMoneyPouchListener(final ProfitPane profitPane) {
        this.profitPane = profitPane;
    }

    @Override
    public void onContentsChanged(final MoneyPouchEvent moneyPouchEvent) {

    }
}
