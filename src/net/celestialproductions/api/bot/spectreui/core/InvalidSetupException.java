package net.celestialproductions.api.bot.spectreui.core;

/**
 * @author Savior
 */
public class InvalidSetupException extends Exception {
    private final String message;

    public InvalidSetupException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
