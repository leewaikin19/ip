package glados.commands;

import glados.local.Storage;
import glados.tasks.TaskList;
import glados.ui.Ui;

/** Command class that will execute user commands */
public class Command {
    protected Boolean isExit = false;
    protected String command;
    protected String query;

    public Command(String command) {
        this.command = command;
        if (command.equals("exit")) {
            isExit = true;
        }
    }

    /**
     * Returns command
     * 
     * @return String command
     */

    public Command(String command, String query) {
        this.command = command;
        this.query = query;
    }

    public String getCommand() {
        return command;
    }

    /**
     * Executes task
     * 
     * @param tasks   task list of the program
     * @param ui      ui of the program
     * @param storage local storage of the program
     */
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        switch (command) {
            case "exit":
                break;
            case "list":
                Ui.show(tasks.toString());
                break;
            case "find":
                TaskList queryResults = tasks.find(query);
                if (queryResults.size() == 0) {
                    Ui.show("There are no search results. Please try again.");
                } else {
                    Ui.show(queryResults.toString());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Returns true if the command exits the program
     * 
     * @return Boolean isExit property
     */

    public Boolean isExit() {
        return isExit;
    }
}
