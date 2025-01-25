public class UpdateTaskCommand extends Command {
    private int index;
    private Boolean isDone;
    private String msg;

    UpdateTaskCommand(String command, Boolean isDone, int index, String msg) {
        super(command);
        this.index = index;
        this.isDone = isDone;
        this.msg = msg;
    }
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task targetTask = tasks.get(index);
        targetTask.isDone = isDone;
        Ui.show(msg + targetTask + "\n");
        storage.saveData(tasks);
    }
}
