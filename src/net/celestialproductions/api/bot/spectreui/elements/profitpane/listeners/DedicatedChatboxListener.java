package net.celestialproductions.api.bot.spectreui.elements.profitpane.listeners;

import com.runemate.game.api.hybrid.entities.definitions.ItemDefinition;
import com.runemate.game.api.hybrid.local.hud.interfaces.Chatbox;
import com.runemate.game.api.script.framework.AbstractBot;
import com.runemate.game.api.script.framework.listeners.ChatboxListener;
import com.runemate.game.api.script.framework.listeners.events.MessageEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import net.celestialproductions.api.bot.spectreui.elements.profitpane.ProfitPane;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Savior
 */
public class DedicatedChatboxListener implements ChatboxListener {
    private final static Pattern DISMANTLE_PATTERN = Pattern.compile("^Spring cleaner dismantled: .+ x \\d+, granting \\d+ bonus XP in .+\\. Springs left: \\d+\\.$");
    private final static Pattern RESEARCH_PATTERN = Pattern.compile("^Item dismantled: .+ x \\d+\\. Springs left: \\d+\\.$");
    private final static Pattern SENT_TO_BANK_PATTERN = Pattern.compile("^Item sent to bank: (?<name>.+) x (?<amount>\\d+)\\.$");
    private final static Map<String, Integer> ID_CACHE = new ConcurrentHashMap<>();
    private final AbstractBot bot;
    private final ProfitPane profitPane;
    private final BooleanProperty trackSpringCleaner;

    public DedicatedChatboxListener(final AbstractBot bot, final ProfitPane profitPane) {
        this.bot = bot;
        this.profitPane = profitPane;
        this.trackSpringCleaner = new SimpleBooleanProperty(false);
    }

    @Override
    public void onMessageReceived(final MessageEvent messageEvent) {
        if (trackSpringCleaner.get() && messageEvent.getType() == Chatbox.Message.Type.SERVER)  {
            bot.getPlatform().invokeLater(() -> {
                final String msg = messageEvent.getMessage();
                Matcher matcher = SENT_TO_BANK_PATTERN.matcher(msg);
                if (matcher.matches()) {
                    final int id = getId(matcher.group("name"));
                    final int amount = Integer.parseInt(matcher.group("amount"));
                    profitPane.addItem(id, amount);
                } else if (DISMANTLE_PATTERN.matcher(msg).matches()) {
                    final int id = getId("Spring");
                    profitPane.addItem(id, 1);
                } else if (RESEARCH_PATTERN.matcher(msg).matches()) {
                    final int id = getId("Spring");
                    profitPane.addItem(id, 1);
                }
            });
        }
    }

    private int getId(final String name) {
        return ID_CACHE.computeIfAbsent(name, n -> {
            final List<ItemDefinition> itemDefinitions = ItemDefinition.get(0, 50000, ItemDefinition.getNamePredicate(name));
            return itemDefinitions.isEmpty() ? -1 : itemDefinitions.get(0).getUnnotedId();
        });
    }

    public void bind(final BooleanProperty property) {
        trackSpringCleaner.bind(property);
    }
}
