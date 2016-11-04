package net.celestialproductions.api.game.breakhandler;

import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class BreakConfigurator extends VBox implements Initializable {
    private final BreakScheduler scheduler;

    public BreakConfigurator(final Mainclass bot, final BreakScheduler scheduler) {
        this.scheduler = scheduler;

        bot.fxmlAttacher().attach(this, "resources/fxml/BreakConfigurator.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {

    }
}
