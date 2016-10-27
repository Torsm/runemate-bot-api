package net.celestialproductions.api.bot.framework.accessors;

import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.AbstractScript;

/**
 * @author Savior
 */
public interface BotAccessor<B extends AbstractBot> {

    B bot();

}
