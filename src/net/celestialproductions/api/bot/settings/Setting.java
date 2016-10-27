package net.celestialproductions.api.bot.settings;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.util.io.ManagedProperties;
import javafx.util.StringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * @author Savior
 */
public class Setting<T> {

    /**
     * Just some default converters
     */
    public final static StringConverter<Boolean> BOOLEAN_STRING_CONVERTER = new BooleanStringConverter();
    public final static StringConverter<Integer> INTEGER_STRING_CONVERTER = new IntegerStringConverter();
    public final static StringConverter<Double>  DOUBLE_STRING_CONVERTER  = new DoubleStringConverter();

    private final String name;
    private final StringConverter<T> conv;
    private final ManagedProperties context;
    private T value = null;

    /**
     * Creates a new setting with bot settings as default
     * @param name Name of the setting
     * @param conv Converter
     */
    public Setting(final String name, final StringConverter<T> conv) {
        this(Environment.getBot().getSettings(), name, conv);
    }

    /**
     * Creates a new setting
     * @param context Either general or bot setting
     * @param name Name of the setting
     * @param conv Converter
     */
    public Setting(final ManagedProperties context, final String name, final StringConverter<T> conv) {
        this.name = name;
        this.conv = conv;
        this.context = context;
    }


    /**
     * @return The availability of the setting
     */
    public boolean isAvailable() {
        return UserSettingsAccessor.settingAvailable(context, name);
    }

    /**
     * Gets the value of the setting and caches it
     * @return Value that was loaded and converted
     */
    public T get() {
        if (value == null)
            value = UserSettingsAccessor.get(context, name, conv);
        return value;
    }

    /**
     * Gets the value of the setting and caches it. Sets a default value if setting is not set.
     * @param defaultValue Default value
     * @return Returns the value, or the default value
     */
    public T get(final T defaultValue) {
        if (value == null) {
            value = UserSettingsAccessor.get(context, name, conv);
        }
        if (value == null) {
            value = set(defaultValue);
        }
        return value;
    }

    /**
     * Sets the value of the setting, value gets cached
     * @param value Value
     * @return Returns value to allow chaining
     */
    public T set(final T value) {
        this.value = UserSettingsAccessor.set(context, name, value, conv);
        return value;
    }

    /**
     * Removes the setting
     */
    public void remove() {
        UserSettingsAccessor.remove(context, name);
    }
}
