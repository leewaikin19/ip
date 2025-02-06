package glados.ui;

import glados.commands.Command;
import glados.exceptions.GladosException;
import glados.local.Storage;
import glados.tasks.TaskList;
import javafx.stage.Stage;

/** Main program class */
public class Glados {
    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    public Glados(String filePath) {
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

    public String getWelcomeMessage() {
        return Ui.getWelcomeMessage();
    }

    public String getResponse(String command, Stage stage) {
        String response = "";
        try {
            Command c = Parser.parse(command);
            response = c.execute(tasks, ui, storage);
            if (c.isExit()) {
                ui.close();

                stage.close();
            }
            return response;
        } catch (GladosException e) {
            return e.getMessage() + "\nPlease try again.";
        }
    }

    public static void main(String[] args) {
        new Glados("data/tasks.txt").run();
    }
}
