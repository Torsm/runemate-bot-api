package net.celestialproductions.api.util.javafx;

import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * @author Savior
 */
public class FXMLAttacher {
    private final AbstractBot bot;

    public FXMLAttacher(final AbstractBot bot) {
        this.bot = bot;
    }

    /**
     * Loads the fxml file of the path and makes the controller its controller. FXML must be fx:root!
     * @param controller Controller
     * @param path Path
     */
    public void attach(final Node controller, final String path) {
        bot.getPlatform().invokeLater(() -> {
            final InputStream stream = Resources.getAsStream(path);

            Platform.runLater(() -> {
                final FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setRoot(controller);
                fxmlLoader.setController(controller);
                try {
                    fxmlLoader.load(stream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}
