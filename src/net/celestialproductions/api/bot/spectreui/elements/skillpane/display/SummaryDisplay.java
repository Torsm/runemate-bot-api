package net.celestialproductions.api.bot.spectreui.elements.skillpane.display;

import com.runemate.game.api.hybrid.local.Skills;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.events.SkillEvent;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.SkillPane;
import net.celestialproductions.api.util.StringUtils;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * @author Savior
 */
public class SummaryDisplay<T extends AbstractBot & Mainclass<T>> extends DataDisplay<T> implements Initializable {

    public SummaryDisplay(final T bot, final SkillPane<T> skillPane) {
        super(bot, skillPane);

        bot.fxmlAttacher().attach(this, "resources/fxml/spectreui/DataDisplay.fxml");
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        super.initialize(location, resources);

        getStyleClass().add("skill-total");

        bot.getPlatform().invokeLater(() -> {
            final int level = Arrays.stream(Skills.getBaseLevels()).sum();
            Platform.runLater(() -> {
                nameLabel.setText("Total");
                typeLabel.setText("Summary");
                levelLabel.setText(Integer.toString(level));
                levelsGainedLabel.setText("0 levels gained");
                experienceLabel.setText("0");
                experienceRateLabel.setText("0 per hour");
                actionsLabel.setText("0");
                actionsRateLabel.setText("0 per hour");
            });
        });

        bot.getEventDispatcher().addListener(this);
        getChildren().addAll();
    }

    @Override
    public void onExperienceGained(final SkillEvent event) {
        if (event.getPrevious() > 0 && event.getCurrent() > 0) {
            bot.getPlatform().invokeLater(() -> {
                final int level = Arrays.stream(Skills.getBaseLevels()).sum();

                experienceCounter.addAndGet(event.getChange());
                actionCounter.incrementAndGet();

                Platform.runLater(() -> {
                    levelLabel.setText(Integer.toString(level));
                    levelsGainedLabel.setText(levelCounter.get() + " levels gained");
                    experienceLabel.setText(StringUtils.metricFormat(experienceCounter.get()));
                    actionsLabel.setText(Integer.toString(actionCounter.get()));
                });
            });
        }
    }

    @Override
    public void onLevelUp(final SkillEvent event) {
        if (event.getPrevious() > 0 && event.getCurrent() > 0) {
            bot.getPlatform().invokeLater(() -> {
                final int level = Arrays.stream(Skills.getBaseLevels()).sum();
                levelCounter.addAndGet(event.getChange());

                Platform.runLater(() -> {
                    levelLabel.setText(Integer.toString(level));
                    levelsGainedLabel.setText(levelCounter.get() + " levels gained");
                });
            });
        }
    }
}
