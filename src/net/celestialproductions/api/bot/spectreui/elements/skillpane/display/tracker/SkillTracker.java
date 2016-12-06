package net.celestialproductions.api.bot.spectreui.elements.skillpane.display.tracker;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.local.Skills;
import com.runemate.game.api.hybrid.util.Time;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SkillTracker<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, SkillListener {
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Button removeButton;
    @FXML
    private Label targetLabel, experienceLeftLabel, estimatedTimeLabel;

    private final T bot;
    private final SkillTrackerPane<T> container;
    private final Skill skill;
    private final int targetLevel;
    private int startLevelBaseExperience;

    public SkillTracker(final T bot, final SkillTrackerPane<T> container, final Skill skill, final int targetLevel) {
        this.bot = bot;
        this.container = container;
        this.skill = skill;
        this.targetLevel = targetLevel;

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/SkillTracker.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        targetLabel.setText("Target level: " + targetLevel);
        experienceLeftLabel.setText("0 left");
        estimatedTimeLabel.setText("00:00:00");
        removeButton.setOnAction(event -> container.remove(this));

        final NumberBinding limitedWidth = Bindings.max(210, widthProperty().multiply(0.5));
        progressBar.maxWidthProperty().bind(limitedWidth);

        bot.getPlatform().invokeLater(() -> {
            startLevelBaseExperience = Skills.getExperienceAt(skill, skill.getBaseLevel());
            final int experience = skill.getExperience();
            onExperienceGained(new SkillEvent(skill, SkillEvent.Type.EXPERIENCE_GAINED, experience, experience));
        });
    }

    public void update(final double experienceRate, final int currentExperience) {
        bot.getPlatform().invokeLater(() -> {
            final int level = skill.getBaseLevel();
            if (level >= targetLevel) {
                Platform.runLater(() -> {
                    container.remove(this);
                    container.add(level + 1);
                });
            } else {
                final double experienceLeft = Skills.getExperienceAt(skill, targetLevel) - currentExperience;
                final long timeLeft = experienceRate == 0 ? 0L : (long) (experienceLeft / experienceRate);

                Platform.runLater(() -> estimatedTimeLabel.setText("TTL: " + Time.format(timeLeft)));
            }
        });
    }

    @Override
    public void onExperienceGained(final SkillEvent event) {
        if (event.getSkill() == skill && event.getPrevious() > 0 && event.getCurrent() > 0) {
            bot.getPlatform().invokeLater(() -> {
                final long experienceLeft = Skills.getExperienceAt(skill, targetLevel) - event.getCurrent();
                final double totalXPNeededForLevel = Skills.getExperienceAt(skill, targetLevel) - startLevelBaseExperience;
                final double currentLevelProgress = (double) event.getCurrent() - startLevelBaseExperience;
                final double progress = currentLevelProgress / totalXPNeededForLevel;

                Platform.runLater(() -> {
                    progressBar.setProgress(progress);
                    experienceLeftLabel.setText(StringUtils.metricFormat(experienceLeft) + " left");
                });
            });
        }
    }

    @Override
    public void onLevelUp(final SkillEvent event) {
        if (event.getSkill() == skill && event.getCurrent() >= targetLevel) {
            Platform.runLater(() -> {
                container.remove(this);
                container.add(event.getCurrent() + 1);
            });
        }
    }

    public Skill getSkill() {
        return skill;
    }

    public int getTargetLevel() {
        return targetLevel;
    }
}
