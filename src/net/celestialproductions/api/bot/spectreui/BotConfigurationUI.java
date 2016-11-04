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
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.core.InvalidSetupException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class BotConfigurationUI<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable {
    @FXML
    private Button startButton;

    private final T bot;

    public BotConfigurationUI(final T bot) {
        this.bot = bot;

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
                bot.timer().start();
                Platform.runLater(() -> bot.extender().botInterfaceProperty().set(bot.spectreUI()));
            } catch (InvalidSetupException ex) {
                ClientUI.showAlert(ex.getMessage());
            }
        }));
    }
}
