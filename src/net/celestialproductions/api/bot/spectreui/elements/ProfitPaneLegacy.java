package net.celestialproductions.api.bot.spectreui.elements;

import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.events.ItemEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class ProfitPaneLegacy<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, InventoryListener {
    private final T bot;

    public ProfitPaneLegacy(final T bot) {
        this.bot = bot;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/ProfitPaneLegacy.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bot.getEventDispatcher().addListener(this);
    }

    @Override
    public void onItemAdded(final ItemEvent itemEvent) {

    }

    @Override
    public void onItemRemoved(final ItemEvent itemEvent) {

    }
}
