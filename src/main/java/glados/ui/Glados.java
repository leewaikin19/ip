package glados.ui;

import glados.commands.Command;
import glados.exceptions.GladosException;
import glados.local.Storage;
import glados.tasks.TaskList;

/** Main program class */
public class Glados {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    Glados(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadData());
        } catch (GladosException e) {
            Ui.show(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        Ui.showWelcomeMessage();
        while (true) {
            String command = ui.readLine();
            try {
                Command c = Parser.parse(command);
                c.execute(tasks, ui, storage);
                if (c.isExit()) {
                    ui.close();
                    break;
                }
            } catch (GladosException e) {
                Ui.show(e.getMessage());
                Ui.show("Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        new Glados("data/tasks.txt").run();
    }
}
