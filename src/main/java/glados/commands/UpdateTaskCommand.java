package glados.commands;

import glados.local.Storage;
import glados.tasks.Task;
import glados.tasks.TaskList;
import glados.ui.Ui;

/** Command to update status of a task */
public class UpdateTaskCommand extends Command {
    private int index;
    private Boolean isDone;
    private String msg;

    public UpdateTaskCommand(String command, Boolean isDone, int index, String msg) {
        super(command);
        this.index = index;
        this.isDone = isDone;
        this.msg = msg;
        assert command != null && !command.isBlank();
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        Task targetTask = tasks.get(index);
        targetTask.setDone(isDone);

        storage.saveData(tasks);
        return msg + targetTask;
    }
}
