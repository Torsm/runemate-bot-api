package net.celestialproductions.api.bot.spectreui.elements.skillpane.display;

import com.runemate.game.api.hybrid.util.calculations.CommonMath;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.SkillListener;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import net.celestialproductions.api.bot.framework.extender.Mainclass;
import net.celestialproductions.api.bot.spectreui.elements.skillpane.SkillPane;
import net.celestialproductions.api.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Savior
 */
public abstract class DataDisplay<T extends AbstractBot & Mainclass<T>> extends VBox implements Initializable, SkillListener {
    @FXML
    protected Label nameLabel, typeLabel, levelLabel, levelsGainedLabel, experienceLabel, experienceRateLabel, actionsLabel, actionsRateLabel;
    @FXML
    private FlowPane dataFlowPane;
    @FXML
    private VBox skillVBox, levelVBox, experienceVBox, actionsVBox;
    @FXML
    private HBox dataHBox;

    protected final T bot;
    protected final SkillPane<T> skillPane;
    protected final BooleanProperty pinned;
    protected final AtomicInteger levelCounter;
    protected final AtomicInteger experienceCounter;
    protected final AtomicInteger actionCounter;

    public DataDisplay(final T bot, final SkillPane<T> skillPane) {
        this.bot = bot;
        this.skillPane = skillPane;
        this.pinned = new SimpleBooleanProperty(false);
        this.levelCounter = new AtomicInteger(0);
        this.experienceCounter = new AtomicInteger(0);
        this.actionCounter = new AtomicInteger(0);
    }

    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        final DoubleBinding elementsWidth = skillVBox.widthProperty().add(levelVBox.widthProperty()).add(experienceVBox.widthProperty()).add(actionsVBox.widthProperty());
        final DoubleBinding spacing = widthProperty().subtract(elementsWidth).divide(3).subtract(10);
        final NumberBinding limitedSpacing = Bindings.max(25, spacing);
        dataFlowPane.hgapProperty().bind(limitedSpacing);
        dataHBox.spacingProperty().bind(limitedSpacing);
    }

    public void update() {
        final long runtime = bot.timer().getRuntime();
        final double experiencePerHour = CommonMath.rate(TimeUnit.HOURS, runtime, experienceCounter.get());
        final double actionsPerHour = CommonMath.rate(TimeUnit.HOURS, runtime, actionCounter.get());

        experienceRateLabel.setText(StringUtils.metricFormat((long) experiencePerHour) + " per hour");
        actionsRateLabel.setText(StringUtils.metricFormat((long) actionsPerHour) + " per hour");
    }

    public boolean isPinned() {
        return pinned.get();
    }

    public void setPinned(final boolean pinned) {
        this.pinned.set(pinned);
    }

    public BooleanProperty pinnedProperty() {
        return pinned;
    }
}
