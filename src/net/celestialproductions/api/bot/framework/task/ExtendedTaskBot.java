package net.celestialproductions.api.bot.framework.task;

import com.runemate.game.api.script.framework.task.Task;
import com.runemate.game.api.script.framework.task.TaskBot;
import com.runemate.game.api.script.framework.task.TaskScript;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Savior
 */
public class ExtendedTaskBot extends TaskBot implements Iterable<Task> {

    /**
     * Iterates through the task tree until one task of type T is found.
     *
     * @param taskClass Class of searched task
     * @return Returns found task or null if no task matched
     */
    @SuppressWarnings("unchecked")
    public final <T extends Task> T findTask(final Class<T> taskClass) {
        for (Task child : this) {
            if (taskClass.isInstance(child)) {
                return (T) child;
            }
        }

        return null;
    }

    /**
     * Pre-order iterator
     */
    @Override
    public final Iterator<Task> iterator() {
        return new Iterator<Task>() {
            final Stack<Task> taskStack = new Stack<>();

            {
                for (int i = getTasks().size(); i > 0; --i) {
                    taskStack.push(getTasks().get(i));
                }
            }

            @Override
            public boolean hasNext() {
                return !taskStack.isEmpty();
            }

            @Override
            public Task next() {
                final Task root = taskStack.pop();

                for (int i = root.getChildren().size(); i > 0; --i) {
                    taskStack.push(root.getChildren().get(i));
                }

                return root;
            }
        };
    }
}
