package net.celestialproductions.api.bot.framework.tree;

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

    private class RootTask extends TreeTask {

        @Override
        public boolean isLeaf() {
            return tree == null;
        }

        @Override
        public TreeTask failureTask() {
            return tree.failureTask();
        }

        @Override
        public void execute() {
            if (tree != null) {
                tree.execute();
            }
        }

        @Override
        public TreeTask successTask() {
            return tree.successTask();
        }

        @Override
        public boolean validate() {
            return tree.validate();
        }
    }
}
