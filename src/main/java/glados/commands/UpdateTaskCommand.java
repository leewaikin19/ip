package glados.commands;

import glados.local.Storage;
import glados.tasks.Task;
import glados.tasks.TaskList;
import glados.ui.Ui;

public class UpdateTaskCommand extends Command {
    private int index;
    private Boolean isDone;
    private String msg;

    public UpdateTaskCommand(String command, Boolean isDone, int index, String msg) {
        super(command);
        this.index = index;
        this.isDone = isDone;
        this.msg = msg;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task targetTask = tasks.get(index);
        targetTask.setDone(isDone);
        Ui.show(msg + targetTask + "\n");
        storage.saveData(tasks);
    }
}
