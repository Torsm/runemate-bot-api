package net.celestialproductions.api.bot.settings.converters;

import javafx.util.StringConverter;

/**
 * @author Savior
 */
public final class EnumStringConverter<E extends Enum<E>> extends StringConverter<E> {
    private final Class<E> enumClass;

    public EnumStringConverter(final Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String toString(final E object) {
        return object.name();
    }

    @Override
    public E fromString(final String string) {
        return Enum.valueOf(enumClass, string);
    }
}
