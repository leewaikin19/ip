package glados.commands;
import glados.local.Storage;
import glados.tasks.TaskList;
import glados.ui.Ui;

/** Command class that will execute user commands */
public class Command {
    protected Boolean isExit = false;
    protected String command;

    public Command(String command) {
        this.command = command;
        if (command.equals("exit")) {
            isExit = true;
        }
    }
    
    /** 
     * Returns command 
     * @return String command
     */
    public String getCommand() {
        return command;
    }
    
    /** 
     * Executes task
     * 
     * @param tasks task list of the program
     * @param ui ui of the program
     * @param storage local storage of the program
     */
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
    
    /** 
     * Returns true if the command exits the program
     * @return Boolean isExit property
     */
    public Boolean isExit() {
        return isExit;
    }
}
