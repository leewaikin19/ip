package glados.commands;

import glados.local.Storage;
import glados.tasks.TaskList;
import glados.ui.Ui;

/** Command class that will execute user commands */
public class Command {
    protected Boolean isExit = false;
    protected String command;
    protected String query;
    private final String HELP_MESSAGE = """
            Glados commands:

            Add a todo entry
                todo [description]

            Add a deadline entry
                deadline [description] /by [due date]

            Add an event entry
                event [description] /from [start date] /to [end date]

            Mark/unmark a task as done/not done
                mark [task number]
                unmark [task number]

            Prints all active tasks
                list

            Delete a task
                delete [task number]

            Find a task by description
                find [search query]

            Exit the program
                exit

            Show this help message.
                help
            """;

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
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        switch (command) {
            case "exit":
                return Ui.getExitMessage();
            case "list":
                return tasks.toString();
            case "find":
                TaskList queryResults = tasks.find(query);
                if (queryResults.size() == 0) {
                    return "There are no search results. Please try again.";
                } else {
                    return queryResults.toString();
                }
            case "help":
                return HELP_MESSAGE;
            default:
                break;
        }
        return "Unkown command. Please try again.";
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
