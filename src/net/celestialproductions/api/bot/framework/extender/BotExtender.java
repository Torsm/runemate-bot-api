package net.celestialproductions.api.bot.framework.extender;

import com.runemate.game.api.client.embeddable.EmbeddableUI;
import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.Execution;
import com.runemate.game.api.script.data.ScriptMetaData;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.beans.property.*;
import javafx.scene.Node;
import net.celestialproductions.api.bot.settings.Setting;
import net.celestialproductions.api.bot.spectreui.BotConfigurationUI;
import net.celestialproductions.api.bot.spectreui.SpectreUI;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;
import net.celestialproductions.api.util.javafx.FXMLAttacher;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO: Rename?
 * @author Savior
 */
public final class BotExtender<T extends AbstractBot & IBotExtender<T>> implements EmbeddableUI {
    private final T bot;
    private final StopWatch timer;
    private final FXMLAttacher fxmlAttacher;
    private final BreakScheduler breakScheduler;
    private final ObjectProperty<Node> botInterfaceProperty;
    private final AtomicBoolean initializedSettings;
    private Antipattern.List antipatterns;
    private String status;

    public BotExtender(final T bot) {
        this.bot = bot;
        this.timer = new StopWatch();
        this.fxmlAttacher = new FXMLAttacher(bot);
        this.breakScheduler = new BreakScheduler(bot);
        this.botInterfaceProperty = new SimpleObjectProperty<>(null);
        this.initializedSettings = new AtomicBoolean(false);
        this.antipatterns = new Antipattern.List();

        setStatus("Waiting for start...");
    }

    /**
     * IMPORTANT:
     * If the bot's main class makes use of the BotExtender system, this method NEEDS to be called in the onStart() method!
     *
     * The method itself inspects the settings and optionally resets them if they are outdated, afterwards it permits the
     * UI to be loaded by setting the initializedSettings flag.
     */
    public void initialize() {
        final Setting<Integer> settingsVersionSetting = new Setting<>("settingsVersion", Setting.INTEGER_STRING_CONVERTER);
        if (settingsVersionSetting.get(0) != bot.settingsVersion()) {
            bot.getSettings().clear();
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
                botInterfaceProperty.set(bot.botConfigurator() != null ? new BotConfigurationUI<>(bot) : new SpectreUI<>(bot));
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

    public void setStatus(final String status) {

        this.status = status;
    }

    public String getStatus() {
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
}
