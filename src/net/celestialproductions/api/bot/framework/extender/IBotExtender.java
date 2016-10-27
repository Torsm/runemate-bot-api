package net.celestialproductions.api.bot.framework.extender;

import com.runemate.game.api.hybrid.util.StopWatch;
import com.runemate.game.api.script.framework.AbstractBot;
import javafx.scene.Node;
import net.celestialproductions.api.bot.spectreui.core.InvalidSetupException;
import net.celestialproductions.api.game.antipattern.Antipattern;
import net.celestialproductions.api.game.breakhandler.BreakScheduler;
import net.celestialproductions.api.util.javafx.FXMLAttacher;

import java.util.Collection;

/**
 * Implement this interface in order to access the BotExtender's features.
 *
 * The advantage for this "bridge" interface is that the main bot class can freely extend any kind of AbstractBot, while
 * maintaining the diversity of extra methods.
 *
 * TODO: rename?
 *
 * @author Savior
 */
public interface IBotExtender<T extends AbstractBot & IBotExtender<T>> {

    /**
     * Initialize a persistent instance of BotExtender in your main class' constructor and pass it as embeddable ui.
     * Call BotExtender#initialize() in the onStart() method of your main class.
     *
     * @return The BotExtender instance
     */
    BotExtender<T> extender();

    /**
     * Implement this method to setup your framework (for example add tasks to a TaskBot), after optionally checking
     * the options the user entered in the bot configurator.
     *
     * Make sure to reset the framework every time this method gets called.
     *
     * @throws InvalidSetupException When the set of options the user entered are not allowed.
     */
    void startButtonPerformed() throws InvalidSetupException;

    /**
     * If the bot has a configuration UI, implement this method and return a persistent instance of it. If your bot does
     * not have any options that need to be configured beforehand, you don't need to implement this method.
     *
     * @return Instance of your UI
     */
    default Node botConfigurator() {
        return null;
    }

    /**
     * Implement this method to specify a condition for the break scheduler. Before any break gets executed, the break
     * scheduler calls this method and only executes the break if it returns true.
     *
     * @return true if the bot is ready to break (for example out of combat or logged out). By default this returns true.
     */
    default boolean readyForBreak() {
        return extender().readyForBreak();
    }

    /**
     * Implement this method and change the return value to some int value. If your bot uses ManagedProperties, it may
     * at some point receive an update where certain saved properties will cause the bot to malfunction.
     *
     * If the return value of this method is not the same as the one saved in the ManagedProperties of a bot, all settings
     * will be reset.
     *
     * It is advised to use this system and increment the return value every time the range of valid values of a setting
     * changes.
     *
     * @return The current bot version's settings version. By default this returns 0.
     */
    default int settingsVersion() {
        return extender().settingsVersion();
    }


    // The rest of the methods are trivial getters and setters and do not need to be implemented at any point.


    default boolean isPremium() {
        return extender().isPremium();
    }

    default void setStatus(final String status) {
        extender().setStatus(status);
    }

    default String getStatus() {
        return extender().getStatus();
    }

    default void setAntipatterns(final Antipattern... antipatterns) {
        extender().setAntipatterns(antipatterns);
    }

    default void setAntipatterns(final Collection<Antipattern> antipatterns) {
        extender().setAntipatterns(antipatterns);
    }

    default Antipattern.List getAntipatterns() {
        return extender().getAntipatterns();
    }

    default BreakScheduler breakScheduler() {
        return extender().breakScheduler();
    }

    default StopWatch timer() {
        return extender().timer();
    }

    default FXMLAttacher fxmlAttacher() {
        return extender().fxmlAttacher();
    }

}
