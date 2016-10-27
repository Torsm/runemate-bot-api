package net.celestialproductions.api.bot.spectreui;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.extender.IBotExtender;
import net.celestialproductions.api.bot.spectreui.core.InvalidSetupException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class BotConfigurationUI<T extends AbstractBot & IBotExtender<T>> extends VBox implements Initializable {
    @FXML
    private Button startButton;

    private final T bot;
    private final SpectreUI<T> spectreUI;

    public BotConfigurationUI(final T bot) {
        this(bot, null);
    }

    public BotConfigurationUI(final T bot, final SpectreUI<T> spectreUI) {
        this.bot = bot;
        this.spectreUI = spectreUI;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/BotConfiguratorUI.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final Node botConfigurator = bot.botConfigurator();
        getChildren().add(0, botConfigurator);
        setVgrow(botConfigurator, Priority.ALWAYS);

        startButton.setOnAction(e -> bot.getPlatform().invokeLater(() -> {
            try {
                bot.startButtonPerformed();
                Platform.runLater(() -> bot.extender().botInterfaceProperty().set(spectreUI != null ? spectreUI : new SpectreUI<>(bot)));
            } catch (InvalidSetupException ex) {
                ClientUI.showAlert(ex.getMessage());
            }
        }));
    }
}
