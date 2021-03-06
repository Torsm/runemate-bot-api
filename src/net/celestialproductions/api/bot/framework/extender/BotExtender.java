package net.celestialproductions.api.bot.framework.extender;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.data.ScriptMetaData;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import net.celestialproductions.api.bot.settings.Setting;
import net.celestialproductions.api.bot.settings.UserSettingsAccessor;
import net.celestialproductions.api.bot.spectreui.BotConfigurationUI;
import net.celestialproductions.api.bot.spectreui.SpectreUI;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;
import net.celestialproductions.api.util.Log;
import net.celestialproductions.api.util.javafx.FXMLAttacher;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Savior
 */
public final class BotExtender<T extends AbstractBot & Mainclass<T>> implements EmbeddableUI {
    private final T bot;
    private final StopWatch timer;
    private final FXMLAttacher fxmlAttacher;
    private final BreakScheduler<T> breakScheduler;
    private final ObjectProperty<Node> botInterfaceProperty;
    private final AtomicBoolean initializedSettings;
    private final AtomicReference<SpectreUI<T>> spectreUI;
    private final StringProperty status;
    private Antipattern.List antipatterns;

    public BotExtender(final T bot) {
        this.bot = bot;
        this.timer = new StopWatch();
        this.fxmlAttacher = new FXMLAttacher(bot);
        this.breakScheduler = new BreakScheduler<>(bot);
        this.botInterfaceProperty = new SimpleObjectProperty<>(null);
        this.initializedSettings = new AtomicBoolean(false);
        this.spectreUI = new AtomicReference<>(null);
        this.status = new SimpleStringProperty("");
        this.antipatterns = new Antipattern.List();

        status.addListener(((observable, oldValue, newValue) -> Log.i("Status: " + newValue)));
    }

    /**
     * IMPORTANT:
     * If the bot's main class makes use of the BotExtender system, this method NEEDS to be called in the onStart() method!
     *
     * The method itself inspects the settings and optionally resets them if they are outdated, afterwards it permits the
     * UI to be loaded by setting the initializedSettings flag.
     */
    public void initialize() {
        final Setting<Integer> settingsVersionSetting = new Setting<>(bot.getSettings(), "settingsVersion", Setting.INTEGER_STRING_CONVERTER);
        if (settingsVersionSetting.get(0) != bot.settingsVersion()) {
            bot.getSettings().clear();
            UserSettingsAccessor.SHARED_SETTINGS.clear();
            settingsVersionSetting.set(bot.settingsVersion());
        }
        initializedSettings.set(true);

        breakScheduler.initialize();
    }

    @Override
    public ObjectProperty<Node> botInterfaceProperty() {
        while(!initializedSettings.get()) {
            Execution.delay(100L);
        }

        if (botInterfaceProperty.get() == null) {
            spectreUI.set(new SpectreUI<>(bot));
            botInterfaceProperty.set(bot.botConfigurator() != null ? new BotConfigurationUI<>(bot) : spectreUI.get());
        }

        return botInterfaceProperty;
    }

    public boolean isPremium() {
        final ScriptMetaData smd = bot.getMetaData();
        return smd != null && (smd.getHourlyPrice().compareTo(BigDecimal.ZERO) != 0 || smd.isLocal());
    }

    public int settingsVersion() {
        return 0;
    }

    public SpectreUI<T> spectreUI() {
        while (spectreUI.get() == null) {
            Execution.delay(100L);
        }

        return spectreUI.get();
    }

    public void setStatus(final String status) {
        Platform.runLater(() -> this.status.set(status));
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setAntipatterns(final Antipattern... antipatterns) {
        this.antipatterns = new Antipattern.List(antipatterns);
    }

    public void setAntipatterns(final Collection<Antipattern> antipatterns) {
        this.antipatterns = new Antipattern.List(antipatterns);
    }

    public Antipattern.List getAntipatterns() {
        return antipatterns;
    }

    public BreakScheduler breakScheduler() {
        return breakScheduler;
    }

    public boolean readyForBreak() {
        return true;
    }

    public StopWatch timer() {
        return timer;
    }

    public FXMLAttacher fxmlAttacher() {
        return fxmlAttacher;
    }

    public static Mainclass instance() {
        return (Mainclass) Environment.getBot();
    }
}
