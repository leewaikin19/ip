package glados.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import glados.tasks.TaskList;
import glados.ui.Ui;
import glados.local.Storage;

public class UpdateTaskCommandTest {
    @Test
    public void markTest() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("");
        AddTaskCommand command = new AddTaskCommand("todo", "description");
        assertEquals(command.description, "description");
        assertEquals(command.command, "todo");
        command.execute(tasks, ui, storage);
        UpdateTaskCommand command2 = new UpdateTaskCommand("mark", true, 0, null);
        command2.execute(tasks, ui, storage);
        assertEquals(tasks.get(0).toString(), "[T][X] description");
    }

    @Test
    public void unmarkTest() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("");
        AddTaskCommand command = new AddTaskCommand("todo", "description");
        assertEquals(command.description, "description");
        assertEquals(command.command, "todo");
        command.execute(tasks, ui, storage);
        UpdateTaskCommand command2 = new UpdateTaskCommand("mark", true, 0, null);
        command2.execute(tasks, ui, storage);
        UpdateTaskCommand command3 = new UpdateTaskCommand("mark", false, 0, null);
        command3.execute(tasks, ui, storage);
        assertEquals(tasks.get(0).toString(), "[T][ ] description");
    }
}
