package net.celestialproductions.api.util;

import javafx.scene.paint.Color;

/**
 * @author Savior
 */
public final class StringUtils {

    public static String metricFormat(long number) {
        final char[] magnitudes = {'k', 'M', 'G', 'T', 'P', 'E'};
        String s;

        if (number >= 0) {
            s = "";
        } else if (number <= -9200000000000000000L) {
            return "-9.2E";
        } else {
            s = "-";
            number = -number;
        }

        if (number < 1000)
            return s + number;

        for (int i = 0; ; i++) {
            if (number < 10000 && number % 1000 >= 100)
                return s + (number / 1000) + '.' + ((number % 1000) / 100) + magnitudes[i];
            number /= 1000;
            if (number < 1000)
                return s + number + magnitudes[i];
        }
    }

    public static String getWebString(final Color color) {
        return String.format( "#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }

    public static String capitalizeFully(final String string) {
        return capitalize(string.toLowerCase());
    }

    public static String capitalize(final Object object) {
        return capitalize(object.toString());
    }

    public static String capitalize(final String string) {
        final StringBuilder sb = new StringBuilder();
        for (String s : string.split(" ")) {
            if (!s.isEmpty())
                sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(" "));
        }

        return sb.toString();
    }

    public static boolean isNumeric(final String string) {
        if (!string.isEmpty()) {
            boolean usedPoint = false;

            final char firstChar = string.charAt(0);
            if (firstChar == '.') {
                usedPoint = true;
            } else if (!Character.isDigit(firstChar) && firstChar != '-' && firstChar != '+') {
                return false;
            }

            for (int i = 1; i < string.length(); i++) {
                final char c = string.charAt(i);
                if (!Character.isDigit(c)) {
                    return false;
                }
                if (c == '.') {
                    if (usedPoint) {
                        return false;
                    }
                    usedPoint = true;
                }
            }

            return true;
        }

        return false;
    }

}
