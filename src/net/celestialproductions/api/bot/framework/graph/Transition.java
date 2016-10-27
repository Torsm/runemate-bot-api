package net.celestialproductions.api.bot.framework.graph;

import net.celestialproductions.api.util.collections.Prioritizable;

import java.util.concurrent.Callable;

/**
 * A transition from one node to another. Transitions can be prioritized, a high priority means the transition will be
 * considered before lower priority ones. As more than one condition of all transitions of a node could be enabled,
 * this may be needed in order to affirm a consistent flow of operations.
 *
 * @author Savior
 */
public final class Transition implements Prioritizable {
    private final Callable<Boolean> condition;
    private final Node followingNode;
    private final int priority;

    public Transition(final Node followingNode) {
        this(null, followingNode);
    }

    public Transition(final Node followingNode, final int priority) {
        this(null, followingNode, priority);
    }

    public Transition(final Callable<Boolean> condition, final Node followingNode) {
        this(condition, followingNode, 0);
    }

    public Transition(final Callable<Boolean> condition, final Node followingNode, final int priority) {
        this.condition = condition;
        this.followingNode = followingNode;
        this.priority = priority;
    }

    /**
     * @return true if either there is no condition or the condition is met.
     */
    public boolean enabled() {
        try {
            return condition == null || condition.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Node getFollowingNode() {
        return followingNode;
    }

    @Override
    public int priority() {
        return priority;
    }
}
