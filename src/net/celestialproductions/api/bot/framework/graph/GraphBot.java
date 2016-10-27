package net.celestialproductions.api.bot.framework.graph;

import com.runemate.game.api.script.framework.LoopingBot;
import com.runemate.game.api.script.framework.LoopingScript;

/**
 * This framework behaves similar to a DFA, it has states and conditioned transitions in order to change states. These
 * conditions can be seen as input, coming from the game, and are the only way to make a graph persistent, as there is
 * no concrete set defined to contain all states, similar to a LinkedList.
 *
 * Graphs are inductive, which allows their nodes to be graphs themselves. As nodes, graphs can not be and accepted
 * ending node, they must transition into a simple, acceptable node instead.
 *
 * Once the current node of a graph is accepted as an ending node, the graph transitions to a following node.
 *
 * As long as the root graph of this class is not null, it will be executed once every loop, which effectively executes
 * one simple node per loop.
 *
 * @author Savior
 */
public abstract class GraphBot extends LoopingBot {
    private Graph rootGraph;

    @Override
    public final void onLoop() {
        if (rootGraph != null) {
            rootGraph.execute();
        }
    }

    /**
     * When the bot starts or settings change, this method can be called.
     *
     * @param startingNode The starting node of the root graph
     */
    public final void newGraph(final Node startingNode) {
        rootGraph = new Graph(startingNode);
    }
}
