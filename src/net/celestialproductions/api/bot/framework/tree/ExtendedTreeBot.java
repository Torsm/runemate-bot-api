package net.celestialproductions.api.bot.framework.tree;

import com.runemate.game.api.script.framework.tree.BranchTask;
import com.runemate.game.api.script.framework.tree.LeafTask;
import com.runemate.game.api.script.framework.tree.TreeBot;
import com.runemate.game.api.script.framework.tree.TreeTask;

/**
 * @author Savior
 */
public class ExtendedTreeBot extends TreeBot {
    private volatile TreeTask tree;

    @Override
    public final TreeTask createRootTask() {
        return new RootTask();
    }

    public void setTree(final TreeTask root) {
        this.tree = root;
    }

    public TreeTask getTree() {
        return tree;
    }

    private class RootTask extends BranchTask {

        @Override
        public TreeTask failureTask() {
            return tree == null ? new EmptyLeaf() : tree.failureTask();
        }

        @Override
        public TreeTask successTask() {
            return tree == null ? new EmptyLeaf() : tree.successTask();
        }

        @Override
        public boolean validate() {
            return tree != null && tree.validate();
        }
    }

    public static class EmptyLeaf extends LeafTask {

        @Override
        public void execute() {

        }
    }
}
