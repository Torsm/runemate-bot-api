package net.celestialproductions.api.bot.spectreui.elements;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;

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
        LOWEST, LOW, HIGH, HIGHEST
    }
}
