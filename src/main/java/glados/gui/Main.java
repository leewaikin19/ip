package glados.gui;

import java.io.IOException;

import glados.ui.Glados;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private Glados glados = new Glados("data/tasks.txt");

    @Override
    public void start(Stage stage) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setGlados(glados, stage);
            fxmlLoader.<MainWindow>getController().initGui();
            stage.show();

            stage.setTitle("Glados");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
