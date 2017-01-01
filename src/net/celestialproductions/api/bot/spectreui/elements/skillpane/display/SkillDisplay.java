package net.celestialproductions.api.bot.spectreui.elements.skillpane.display;

import com.runemate.game.api.hybrid.local.Skill;
import com.runemate.game.api.hybrid.util.calculations.CommonMath;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.SkillPane;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.display.diagram.DiagramEntry;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.display.tracker.SkillTrackerPane;
import net.celestialproductions.api.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Savior
 */
public class SkillDisplay<T extends AbstractBot & Mainclass<T>> extends DataDisplay<T> implements Initializable, ListChangeListener<Node>, ChangeListener<DataDisplay<T>> {
    private final Skill skill;
    private final ObservableList<Node> listedEntries;
    private final DiagramEntry diagramEntry;
    private final SkillTrackerPane<T> skillTrackerPane;
    private final AtomicInteger currentExperience;

    public SkillDisplay(final T bot, final SkillPane<T> skillPane, final Skill skill) {
        super(bot, skillPane);
        this.skill = skill;
        this.listedEntries = skillPane.getDiagramPane().getChildren();
        this.diagramEntry = new DiagramEntry();
        this.skillTrackerPane = new SkillTrackerPane<>(bot, skill);
        this.currentExperience = new AtomicInteger(0);

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/DataDisplay.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);

        getStyleClass().add("skill-" + skill.name().toLowerCase());
        diagramEntry.getStyleClass().addAll("skill-" + skill.name().toLowerCase(), "diagram-entry");

        bot.getPlatform().invokeLater(() -> {
            final String name = StringUtils.capitalizeFully(skill.name());
            final String type = skill.isMembersOnly() ? "Member skill" : "Free skill";
            Platform.runLater(() -> {
                nameLabel.setText(name);
                typeLabel.setText(type);
            });
        });

        diagramEntry.setOnMouseClicked(event -> skillPane.setPinnedDisplay(isPinned() ? null : this));
        diagramEntry.hoverProperty().addListener(((observable, oldValue, newValue) -> skillPane.setShownDisplay(newValue ? this : skillPane.getPinnedDisplay())));

        bot.getEventDispatcher().addListener(this);
        listedEntries.addListener(this);
        listedEntries.add(diagramEntry);

        getChildren().add(skillTrackerPane);
    }

    @Override
    public void update() {
        final long runtime = bot.timer().getRuntime();
        final double experiencePerHour = CommonMath.rate(TimeUnit.HOURS, runtime, experienceCounter.get());
        final double experienceRate = CommonMath.rate(TimeUnit.MILLISECONDS, runtime, experienceCounter.get());
        final double actionsPerHour = CommonMath.rate(TimeUnit.HOURS, runtime, actionCounter.get());

        experienceRateLabel.setText(StringUtils.metricFormat((long) experiencePerHour) + " per hour");
        actionsRateLabel.setText(StringUtils.metricFormat((long) actionsPerHour) + " per hour");

        skillTrackerPane.update(experienceRate, currentExperience.get());
    }

    @Override
    public void onExperienceGained(final SkillEvent event) {
        if (event.getSkill() == skill && event.getPrevious() > 0 && event.getCurrent() > 0) {
            bot.getPlatform().invokeLater(() -> {
                currentExperience.set(event.getCurrent());
                experienceCounter.addAndGet(event.getChange());
                actionCounter.incrementAndGet();

                final int level = skill.getBaseLevel();
                final int percentage = 100 - skill.getExperienceToNextLevelAsPercent();
                final double radius = 30.0 + (percentage * 0.7);

                Platform.runLater(() -> {
                    levelLabel.setText(Integer.toString(level));
                    levelsGainedLabel.setText(levelCounter.get() + " levels gained");
                    experienceLabel.setText(StringUtils.metricFormat(experienceCounter.get()));
                    actionsLabel.setText(Integer.toString(actionCounter.get()));
                    diagramEntry.setRadius(radius);
                });
            });
        }
    }

    @Override
    public void onLevelUp(final SkillEvent event) {
        if (event.getSkill() == skill && event.getPrevious() > 0 && event.getCurrent() > 0) {
            bot.getPlatform().invokeLater(() -> {
                final int level = event.getCurrent();
                levelCounter.addAndGet(event.getChange());

                Platform.runLater(() -> {
                    levelLabel.setText(Integer.toString(level));
                    levelsGainedLabel.setText(levelCounter.get() + " levels gained");
                });
            });
        }
    }

    @Override
    public void onChanged(final Change<? extends Node> c) {
        final double length = 360.0 / (double) listedEntries.size();
        final double angle = (listedEntries.size() - 1 - listedEntries.indexOf(diagramEntry)) * length;
        diagramEntry.setLength(angle, length);
        diagramEntry.setStrokeWidth(listedEntries.size() == 1 ? 0 : 2);
    }

    @Override
    public void changed(final ObservableValue<? extends DataDisplay<T>> observable, final DataDisplay<T> oldValue, final DataDisplay<T> newValue) {
        diagramEntry.changeOpacity(newValue == this || newValue instanceof SummaryDisplay ? 1.0 : 0.7);
    }
}
