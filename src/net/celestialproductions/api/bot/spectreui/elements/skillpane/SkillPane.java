package net.celestialproductions.api.bot.spectreui.elements.skillpane;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Savior
 */
public class SkillPane<T extends AbstractBot & Mainclass<T>> extends HBox implements Initializable, SkillListener {
    @FXML
    private Pane diagramPane, backgroundPane;
    @FXML
    private StackPane informationPane;

    private final T bot;
    private final Set<Skill> skillsListed;
    private final ObjectProperty<SkillData<T>> shownData;
    private final ObjectProperty<SkillData<T>> pinnedData;

    public SkillPane(final T bot) {
        this.bot = bot;
        this.skillsListed = new HashSet<>();
        this.shownData = new SimpleObjectProperty<>(null);
        this.pinnedData = new SimpleObjectProperty<>(null);

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillPane.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bot.getEventDispatcher().addListener(this);

        shownData.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                informationPane.getChildren().add(newValue);
            }
            if (oldValue != null) {
                informationPane.getChildren().remove(oldValue);
            }
        });

        pinnedData.addListener(((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.setPinned(false);
            }
            if (newValue != null) {
                newValue.setPinned(true);
            }
        }));
    }

    public void add(final SkillEvent event) {
        final SkillData<T> data = new SkillData<>(bot, event.getSkill(), this);
        data.onExperienceGained(event);
        skillsListed.add(event.getSkill());
    }

    public void remove(final Skill skill) {
        skillsListed.remove(skill);
    }

    @Override
    public void onExperienceGained(final SkillEvent event) {
        if (event.getChange() > 0 && event.getPrevious() > 0 && !skillsListed.contains(event.getSkill())) {
            Platform.runLater(() -> add(event));
        }
    }

    public Pane getDiagramPane() {
        return diagramPane;
    }

    public Pane getBackgroundPane() {
        return backgroundPane;
    }

    public SkillData<T> getPinnedData() {
        return pinnedData.get();
    }

    public void setPinnedData(final SkillData<T> pinnedData) {
        this.pinnedData.set(pinnedData);
    }

    public ObjectProperty<SkillData<T>> pinnedDataProperty() {
        return pinnedData;
    }

    public SkillData<T> getShownData() {
        return shownData.get();
    }

    public void setShownData(final SkillData<T> shownData) {
        this.shownData.set(shownData);
    }

    public ObjectProperty<SkillData<T>> shownDataProperty() {
        return shownData;
    }
}
