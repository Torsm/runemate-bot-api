package net.celestialproductions.api.bot.framework.graph;

/**
 * @author Savior
 */
public class Graph extends Node {
    private Node currentNode;

    public Graph(final Node startNode) {
        currentNode = startNode;
    }

    @Override
    public final void execute() {
        currentNode.execute();
        currentNode = currentNode.nextState();
    }

    @Override
    public Node nextState() {
        if (!currentNode.isAccepted())
            return this;
        return super.nextState();
    }
}
