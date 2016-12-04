package net.celestialproductions.api.util;

import com.runemate.game.api.hybrid.Environment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Savior
 */
public final class Log {
    private final static DateFormat FORMAT = new SimpleDateFormat("[HH:mm:ss]");

    public static void e(final String message) {
        log(Level.ERROR, message);
    }

    public static void d(final String message) {
        log(Level.DEBUG, message);
    }

    public static void i(final String message) {
        log(Level.INFO, message);
    }

    public static void log(final Level level, final String message) {
        final String timestamp = FORMAT.format(new Date());
        final StringBuilder b = new StringBuilder();
        b.append(timestamp);
        if (level == Level.ERROR || level == Level.DEBUG)
            b.append("[").append(StringUtils.capitalize(level)).append("]");
        b.append(" ").append(message);

        if (level == Level.ERROR) {
            System.err.println(b.toString());
        } else if (level == Level.DEBUG) {
            if (Environment.isSDK())
                System.out.println(b.toString());
        } else {
            System.out.println(b.toString());
        }
    }

    public enum Level {
        ERROR, DEBUG, INFO
    }
}
