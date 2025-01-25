package glados.commands;
import glados.local.Storage;
import glados.tasks.TaskList;
import glados.ui.Ui;

public class Command {
    protected Boolean isExit = false;
    protected String command;

    public Command(String command) {
        this.command = command;
        if (command.equals("exit")) {
            isExit = true;
        }
    }
    public String getCommand() {
        return command;
    }
    
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        switch (command) {
            case "exit":
                break;
            case "list":
                Ui.show(tasks.toString());
                break;
            default:
                break;
        }
    }
    public Boolean isExit() {
        return isExit;
    }
}
