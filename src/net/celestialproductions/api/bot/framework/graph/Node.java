package net.celestialproductions.api.bot.framework.graph;

import com.runemate.game.api.hybrid.Environment;
import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;
import net.celestialproductions.api.bot.framework.accessors.PlayerAccessor;
import net.celestialproductions.api.bot.framework.accessors.BotAccessor;
import net.celestialproductions.api.util.collections.PriorityComparator;

import java.util.*;

/**
 * A node of a graph gets executed if the graph's current node points to it. After it got executed, it optionally
 * transitions to a new node, or remains the current node.
 *
 * @author Savior
 */
public class Node<B extends GraphBot> implements PlayerAccessor, BotAccessor<B> {
    private final List<Transition> transitions = new ArrayList<>();
    private final boolean accepted;
    private Player local;
    private B bot;

    public Node() {
        this(false);
    }

    public Node(final boolean accepted) {
        this.accepted = accepted;
    }

    public void execute() {

    }

    public Node nextState() {
        final Transition t = transitions.stream().filter(Transition::enabled).findFirst().orElse(null);
        return t == null ? this : t.getFollowingNode();
    }

    public final void addTransition(final Transition... t) {
        transitions.addAll(Arrays.asList(t));
        transitions.sort(new PriorityComparator());
    }

    public final void removeTransition(final Transition t) {
        transitions.remove(t);
    }

    public final boolean isAccepted() {
        return accepted;
    }

    @Override
    public final Player local() {
        if (local == null || !local.isValid())
            local = Players.getLocal();
        return local;
    }

    @Override
    public final B bot() {
        if (bot == null)
            bot = (B) Environment.getBot();
        return bot;
    }
}
