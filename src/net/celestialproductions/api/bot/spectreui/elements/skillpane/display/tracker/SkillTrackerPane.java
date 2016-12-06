package net.celestialproductions.api.bot.spectreui.elements.skillpane.display.tracker;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.celestialproductions.api.bot.framework.extender.Mainclass;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Savior
 */
public class SkillTrackerPane<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable {
    @FXML
    private Spinner<Integer> levelSpinner;
    @FXML
    private Button addTrackerButton;

    private final T bot;
    private final Skill skill;
    private final Map<Integer, SkillTracker<T>> listedTrackers;
    private int maxLevel;

    public SkillTrackerPane(final T bot, final Skill skill) {
        this.bot = bot;
        this.skill = skill;
        this.listedTrackers = new ConcurrentHashMap<>();
        this.maxLevel = 99;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillTrackerPane.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        bot.getPlatform().invokeLater(() -> {
            maxLevel = skill.getMaxLevel();
            final int nextLevel = skill.getBaseLevel() + 1;
            Platform.runLater(() -> {
                levelSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxLevel));
                levelSpinner.getValueFactory().setValue(nextLevel);
                addTrackerButton.setOnAction(event -> add(levelSpinner.getValue()));
                add(nextLevel);
            });
        });
    }

    public void update(final double experienceRate, final int currentExperience) {
        listedTrackers.values().forEach(t -> t.update(experienceRate, currentExperience));
    }

    public void add(final int targetLevel) {
        if (targetLevel <= maxLevel && targetLevel > 0 && !listedTrackers.containsKey(targetLevel)) {
            final SkillTracker<T> tracker = new SkillTracker<>(bot, this, skill, targetLevel);
            listedTrackers.put(targetLevel, tracker);
            bot.getEventDispatcher().addListener(tracker);
            getChildren().addAll(tracker);
        }
    }

    public void remove(final SkillTracker<T> skillTracker) {
        listedTrackers.remove(skillTracker.getTargetLevel());
        bot.getEventDispatcher().removeListener(skillTracker);
        getChildren().remove(skillTracker);
    }
}
