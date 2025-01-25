package glados.commands;
import org.junit.jupiter.api.Test;

import glados.tasks.TaskList;
import glados.ui.Ui;
import glados.local.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTaskCommandTest {
    @Test
    public void todoTest() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("");
        AddTaskCommand command = new AddTaskCommand("todo", "description");
        assertEquals(command.description, "description");
        assertEquals(command.command, "todo");
        command.execute(tasks, ui, storage);
        assertEquals(tasks.get(0).toString(), "[T][ ] description");
    }

    @Test
    public void deadlineTest() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("");
        AddTaskCommand command = new AddTaskCommand("deadline", "description", "Monday");
        assertEquals(command.description, "description");
        assertEquals(command.command, "deadline");
        assertEquals(command.by, "Monday");
        command.execute(tasks, ui, storage);
        assertEquals(tasks.get(0).toString(), "[D][ ] description (by: Monday)");
    }

    @Test
    public void eventTest() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("");
        AddTaskCommand command = new AddTaskCommand("event", "description", "Monday", "Sunday");
        assertEquals(command.description, "description");
        assertEquals(command.command, "event");
        assertEquals(command.from, "Monday");
        assertEquals(command.to, "Sunday");
        command.execute(tasks, ui, storage);
        assertEquals(tasks.get(0).toString(), "[E][ ] description (from: Monday to: Sunday)");
    }
}
