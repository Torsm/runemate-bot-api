package net.celestialproductions.api.bot.spectreui;

import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.IndependentLoopingThread;
import net.celestialproductions.api.bot.framework.extender.IBotExtender;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SpectreUI<T extends AbstractBot & IBotExtender<T>> extends VBox implements Initializable {
    @FXML
    private Button changeOptionsButton;
    @FXML
    private Label statusLabel, runtimeLabel;
    @FXML
    private VBox tabContainer;

    private final T bot;

    public SpectreUI(final T bot) {
        this.bot = bot;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SpectreUI.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        if (bot.botConfigurator() != null) {
            changeOptionsButton.setOnAction(e -> bot.extender().botInterfaceProperty().set(new BotConfigurationUI<>(bot, this)));
        } else {
            changeOptionsButton.setVisible(false);
            changeOptionsButton.setDisable(true);
        }

        new IndependentLoopingThread("SpectreUI update thread", this::update, 1000L).start();
    }

    public void update() {
        final String time = bot.timer().getRuntimeAsString();
        final String status = bot.getStatus();
        Platform.runLater(() -> {
            runtimeLabel.setText(time);
            statusLabel.setText(status);
        });
    }
}
