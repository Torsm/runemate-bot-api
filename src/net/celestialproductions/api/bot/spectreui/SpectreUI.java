package net.celestialproductions.api.bot.spectreui;

import com.runemate.game.api.client.ClientUI;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.IndependentLoopingThread;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.core.InvalidSetupException;
import net.celestialproductions.api.bot.spectreui.elements.ProfitPaneLegacy;
import net.celestialproductions.api.bot.spectreui.elements.Tab;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.SkillPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SpectreUI<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable {
    @FXML
    private Button changeOptionsButton;
    @FXML
    private Label statusLabel, runtimeLabel;
    @FXML
    private VBox tabContainer;

    private final T bot;
    private SkillPane<T> skillPane;
    private ProfitPaneLegacy<T> profitPane;

    public SpectreUI(final T bot) {
        this.bot = bot;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SpectreUI.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        if (bot.botConfigurator() != null) {
            changeOptionsButton.setOnAction(e -> bot.extender().botInterfaceProperty().set(new BotConfigurationUI<>(bot)));
        } else {
            changeOptionsButton.setVisible(false);
            changeOptionsButton.setDisable(true);
            bot.getPlatform().invokeLater(() -> {
                try {
                    bot.setStatus("Starting...");
                    bot.startButtonPerformed();
                    bot.timer().start();
                } catch (InvalidSetupException e) {
                    ClientUI.showAlert(e.getMessage());
                    bot.stop();
                }
            });
        }

        statusLabel.textProperty().bind(bot.extender().statusProperty());

        profitPane = new ProfitPaneLegacy<>(bot);
        add(new Tab("Profit", profitPane, Tab.Priority.LOWEST));

        skillPane = new SkillPane<>(bot);
        add(new Tab("Skills", skillPane, Tab.Priority.LOW));

        new IndependentLoopingThread(bot, "SpectreUI update thread", this::update, 1000L).start();
    }

    public void update() {
        final String time = bot.timer().getRuntimeAsString();
        Platform.runLater(() -> {
            skillPane.update();
            runtimeLabel.setText(time);
        });
    }

    public void add(final Tab... tabs) {
        Platform.runLater(() -> {
            final List<Tab> list = new ArrayList<>();
            tabContainer.getChildren().stream().map(node -> (Tab) node).forEach(list::add);
            list.addAll(Arrays.asList(tabs));
            list.sort(((o1, o2) -> o2.priority().ordinal() - o1.priority().ordinal()));
            tabContainer.getChildren().setAll(list);
        });

    }

    public void remove(final Tab... tabs) {
        Platform.runLater(() -> tabContainer.getChildren().removeAll(tabs));
    }
}
