public class RemoveTaskCommand extends Command {
    private int index;

    RemoveTaskCommand(String command, int index) {
        super(command);
        this.index = index;
    }
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task targetTask = tasks.get(index);
        tasks.remove(index);
        Ui.show("Got it. I've removed this task:\n" + targetTask
                    + "\nNow you have " + tasks.size() + " tasks in the list.\n");
        storage.saveData(tasks);
    }
}
