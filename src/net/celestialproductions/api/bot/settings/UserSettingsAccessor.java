package net.celestialproductions.api.bot.settings;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.util.io.ManagedProperties;
import javafx.util.StringConverter;

import java.io.File;

/**
 * @author Savior
 */
public class UserSettingsAccessor {
    public final static ManagedProperties SHARED_SETTINGS;

    static {
        final File settingFile = new File(Environment.getSharedStorageDirectory() + File.separator + "Celestial Productions" + File.separator + "generalsettings.xml");
        if (!settingFile.exists())
            settingFile.mkdirs();
        SHARED_SETTINGS = new ManagedProperties(settingFile);
    }

    /**
     * Gets a value of a setting
     * @param context Either general or bot setting
     * @param name Name of the setting
     * @param conv Converter that converts the setting's String value to T
     * @param defaultValue Default value if no value was found
     * @param <T> Type of the setting you want to store/read
     * @return Return the converted value or the default value
     */
    public static <T> T get(final ManagedProperties context, final String name, final StringConverter<T> conv, final T defaultValue) {
        final String value = context.getProperty(name);
        try {
            return value == null ? defaultValue : conv.fromString(value);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static <T> T get(final ManagedProperties context, final String name, final StringConverter<T> conv) {
        final String value = context.getProperty(name);
        try {
            return value == null ? null : conv.fromString(value);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Sets a value to a setting
     * @param context Either general or bot setting
     * @param name Name of the setting
     * @param val Value to be saved
     * @param conv Converter that converts the value to a String that can be saved in the ManagedProperties
     * @param <T> Type of the value
     * @return Returns the value to allow chaining
     */
    public static <T> T set(final ManagedProperties context, final String name, final T val, final StringConverter<T> conv) {
        context.setProperty(name, conv.toString(val));
        return val;
    }

    /**
     * @param context Either general or bot setting
     * @param name Name of the setting
     * @return Returns if the setting is available, i.e. has been set before
     */
    public static boolean settingAvailable(final ManagedProperties context, final String name) {
        return context.getProperty(name) != null;
    }

    /**
     * Removes a setting
     * @param context Either general or bot settings
     * @param name Name of the setting
     */
    public static void remove(final ManagedProperties context, final String name) {
        context.remove(name);
    }
}
