package net.celestialproductions.api.bot.spectreui.elements;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public final class Tab extends TitledPane {
    private final Priority priority;

    public Tab(final String title, final Node content, final Priority priority) {
        super(title, content);
        this.priority = priority;
        setExpanded(false);
        setAnimated(true);
    }

    public Priority priority() {
        return priority;
    }

    public enum Priority {
        LOWEST, LOW, AUTOMATIC, MANUAL, HIGHEST
    }
}
