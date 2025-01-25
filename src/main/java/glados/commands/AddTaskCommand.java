package glados.commands;

import java.time.LocalDateTime;

import glados.local.Storage;
import glados.tasks.Deadline;
import glados.tasks.Event;
import glados.tasks.Task;
import glados.tasks.TaskList;
import glados.tasks.Todo;
import glados.ui.Ui;
import glados.exceptions.CommandException;

/** Command to add a task to the list */
public class AddTaskCommand extends Command {
    /** Description of the task */
    protected String description;

    /** By field for Deadline */
    protected String by;

    /** By field as a DateTime object for Deadline */
    protected LocalDateTime byDateTime;
    /** From field for Event */
    protected String from;
    /** To field for Event */
    protected String to;
    /** From field as a DateTime object for Event */
    protected LocalDateTime fromDateTime;
    /** To field as a DateTime object for Event */
    protected LocalDateTime toDateTime;

    public AddTaskCommand(String command, String description) throws CommandException {
        super(command);
        if (!command.equals("todo")) {
            throw new CommandException("An unexpected error has occured");
        }
        if (description.isBlank()) {
            throw new CommandException("Todo description cannot be blank");
        }
        this.description = description;
    }

    public AddTaskCommand(String command, String description, String by) throws CommandException {
        super(command);
        if (!command.equals("deadline")) {
            throw new CommandException("An unexpected error has occured");
        }
        if (description.isBlank()) {
            throw new CommandException("Deadline description cannot be blank");
        }
        this.description = description;
        this.by = by;
    }

    public AddTaskCommand(String command, String description, LocalDateTime by) throws CommandException {
        super(command);
        if (!command.equals("deadline")) {
            throw new CommandException("An unexpected error has occured");
        }
        if (description.isBlank()) {
            throw new CommandException("Deadline description cannot be blank");
        }
        this.description = description;
        this.byDateTime = by;
    }

    public AddTaskCommand(String command, String description, String from, String to)
            throws CommandException {
        super(command);
        if (!command.equals("event")) {
            throw new CommandException("An unexpected error has occured");
        }
        if (description.isBlank()) {
            throw new CommandException("Event description cannot be blank");
        }
        this.description = description;
        this.from = from;
        this.to = to;
    }

    public AddTaskCommand(String command, String description,
            LocalDateTime from, LocalDateTime to) throws CommandException {
        super(command);
        if (!command.equals("event") || from == null || to == null) {
            throw new CommandException("An unexpected error has occured");
        }
        if (description.isBlank()) {
            throw new CommandException("Event description cannot be blank");
        }
        this.description = description;
        this.fromDateTime = from;
        this.toDateTime = to;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) throws CommandException {
        Task newItem;
        if (getCommand().equals("todo")) {
            newItem = new Todo(description);
        } else if (getCommand().equals("deadline")) {
            newItem = byDateTime == null
                    ? new Deadline(description, by)
                    : new Deadline(description, byDateTime, by);
        } else if (getCommand().equals("event")) {
            newItem = fromDateTime == null || toDateTime == null
                    ? new Event(description, from, to)
                    : new Event(description, fromDateTime, toDateTime, from, to);
        } else {
            throw new CommandException("Unknown error has occured");
        }
        tasks.add(newItem);
        Ui.show("Got it. I've added this task:\n" + newItem
                + "\nNow you have " + tasks.size() + " tasks in the list.\n");
        storage.saveData(tasks);
    }
}
