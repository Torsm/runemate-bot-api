package net.celestialproductions.api.bot.spectreui.elements.profitpane;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.script.data.GameType;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.InventoryListener;
import com.runemate.game.api.script.framework.listeners.MoneyPouchListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.elements.profitpane.listeners.DedicatedChatboxListener;
import net.celestialproductions.api.bot.spectreui.elements.profitpane.listeners.DedicatedInventoryListener;
import net.celestialproductions.api.bot.spectreui.elements.profitpane.listeners.DedicatedMoneyPouchListener;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class ProfitPane<T extends AbstractBot & Mainclass<T>> extends HBox implements Initializable {
    @FXML
    private CheckBox springCleanerCheckBox;

    private final T bot;

    public ProfitPane(final T bot) {
        this.bot = bot;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/ProfitPane.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bot.getPlatform().invokeLater(() -> {
            final GameType gameType = Environment.getGameType();
            Platform.runLater(() -> {
                final InventoryListener inventoryListener = new DedicatedInventoryListener(this);
                bot.getPlatform().invokeLater(() -> {
                    bot.getEventDispatcher().addListener(inventoryListener);
                });

                if (gameType == GameType.RS3) {
                    final MoneyPouchListener moneyPouchListener = new DedicatedMoneyPouchListener(this);
                    final DedicatedChatboxListener chatboxListener = new DedicatedChatboxListener(bot, this);
                    bot.getPlatform().invokeLater(() -> {
                        bot.getEventDispatcher().addListener(moneyPouchListener);
                        bot.getEventDispatcher().addListener(chatboxListener);
                        chatboxListener.bind(springCleanerCheckBox.selectedProperty());
                    });
                }
            });
        });
    }

    public void addItem(final int id, final int amount) {

    }
}
