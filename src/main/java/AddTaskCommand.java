import java.time.LocalDateTime;

public class AddTaskCommand extends Command {
    private String description;
    private String by;
    private LocalDateTime byDateTime;
    private String from;
    private String to;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    AddTaskCommand(String command, String description) throws CommandException {
        super(command);
        if (!command.equals("todo")) {
            throw new CommandException("An unexpected error has occured");
        }
        if (description.isBlank()) {
            throw new CommandException("Todo description cannot be blank");
        }
        this.description = description;
    }
    AddTaskCommand(String command, String description, String by) throws CommandException {
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
    AddTaskCommand(String command, String description, LocalDateTime by) throws CommandException {
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

    AddTaskCommand(String command, String description, String from, String to) 
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
    AddTaskCommand(String command, String description, 
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
        if (getCommand().equals("todo")){
            newItem = new Todo(description);
        } else if (getCommand().equals("deadline")){
            newItem = byDateTime == null 
                    ? new Deadline(description, by)
                    : new Deadline(description, byDateTime, by);
        } else if (getCommand().equals("event")){
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
