package net.celestialproductions.api.bot.spectreui.elements.skillpane;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.Resources;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.display.DataDisplay;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.display.SkillDisplay;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.display.SummaryDisplay;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Savior
 */
public class SkillPane<T extends AbstractBot & Mainclass<T>> extends HBox implements Initializable, SkillListener {
    @FXML
    private Label statusLabel;
    @FXML
    private Pane diagramPane;

    private final T bot;
    private final Map<Skill, DataDisplay<T>> skillsListed;
    private final SummaryDisplay<T> summaryDisplay;
    private final ObjectProperty<DataDisplay<T>> shownDisplay;
    private final ObjectProperty<DataDisplay<T>> pinnedDisplay;

    public SkillPane(final T bot) {
        this.bot = bot;
        this.skillsListed = new ConcurrentHashMap<>();
        this.summaryDisplay = new SummaryDisplay<>(bot, this);
        this.shownDisplay = new SimpleObjectProperty<>(null);
        this.pinnedDisplay = new SimpleObjectProperty<>(null);

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillPane.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bot.getPlatform().invokeLater(() -> {
            try {
                final String vanity = Resources.getAsURL("resources/css/VanityItem.css").toURI().toString();
                final String tracker = Resources.getAsURL("resources/css/SkillPane.css").toURI().toString();
                Platform.runLater(() -> getStylesheets().addAll(vanity, tracker));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

        bot.getEventDispatcher().addListener(this);

        shownDisplay.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                getChildren().remove(oldValue);
            }
            if (newValue != null) {
                getChildren().add(newValue);
            }
        });

        pinnedDisplay.addListener(((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.setPinned(false);
            }
            if (newValue != null) {
                newValue.setPinned(true);
            } else {
                setPinnedDisplay(summaryDisplay);
            }
        }));

        setShownDisplay(summaryDisplay);
        setPinnedDisplay(summaryDisplay);
    }

    public void add(final SkillEvent event) {
        final SkillDisplay<T> dataDisplay = new SkillDisplay<>(bot, this, event.getSkill());
        dataDisplay.changed(null, null, getShownDisplay());
        dataDisplay.onExperienceGained(event);
        shownDisplay.addListener(dataDisplay);
        skillsListed.put(event.getSkill(), dataDisplay);
    }

    public void update() {
        skillsListed.values().forEach(DataDisplay::update);
        summaryDisplay.update();
    }

    @Override
    public void onExperienceGained(final SkillEvent event) {
        if (event.getChange() > 0 && event.getPrevious() > 0 && !skillsListed.containsKey(event.getSkill())) {
            Platform.runLater(() -> {
                if (skillsListed.isEmpty())
                    statusLabel.setText("Hover or click the diagram");
                add(event);
            });
        }
    }

    public Pane getDiagramPane() {
        return diagramPane;
    }

    public DataDisplay<T> getPinnedDisplay() {
        return pinnedDisplay.get();
    }

    public void setPinnedDisplay(final DataDisplay<T> pinnedDisplay) {
        this.pinnedDisplay.set(pinnedDisplay);
    }

    public ObjectProperty<DataDisplay<T>> pinnedDisplayProperty() {
        return pinnedDisplay;
    }

    public DataDisplay<T> getShownDisplay() {
        return shownDisplay.get();
    }

    public void setShownDisplay(final DataDisplay<T> shownDisplay) {
        this.shownDisplay.set(shownDisplay);
    }

    public ObjectProperty<DataDisplay<T>> shownDisplayProperty() {
        return shownDisplay;
    }
}
