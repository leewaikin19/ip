public class Command {
    private Boolean isExit = false;
    private String command;

    Command(String command) {
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
