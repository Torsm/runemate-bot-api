package net.celestialproductions.api.game;

import com.runemate.game.api.hybrid.local.hud.interfaces.ChatDialog;
import com.runemate.game.api.hybrid.util.Regex;

import java.util.regex.Pattern;

/**
 * @author Savior
 */
public final class Dialog {
    private final Pattern[] patterns;
    private Object option;

    public Dialog(final String... strings) {
        this(Regex.getPatternsForExactStrings(strings).toArray(new Pattern[0]));
    }

    public Dialog(final Pattern... patterns) {
        this.patterns = patterns;
    }

    public boolean isAvailable() {
        option = ChatDialog.getContinue();
        if (option == null) {
            option = ChatDialog.getOption(patterns);
        }

        return option != null;
    }

    public boolean continueDialog() {
        if (option != null) {
            if (option instanceof ChatDialog.Continue) {
                return ((ChatDialog.Continue) option).select();
            } else if (option instanceof ChatDialog.Option){
                return ((ChatDialog.Option) option).select();
            }
        }
        return false;
    }
}
