package glados.commands;

import glados.local.Storage;
import glados.tasks.Task;
import glados.tasks.TaskList;
import glados.ui.Ui;

/** Command to remove a task by index */
public class RemoveTaskCommand extends Command {
    private int index;

    public RemoveTaskCommand(String command, int index) {
        super(command);
        this.index = index;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task targetTask = tasks.get(index);
        tasks.remove(index);

        storage.saveData(tasks);
        return "Got it. I've removed this task:\n" + targetTask
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
