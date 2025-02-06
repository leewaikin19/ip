package glados.gui;

import glados.ui.Glados;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/pfp.png"));
    private Image gladosImage = new Image(this.getClass().getResourceAsStream("/images/pfp2.png"));
    private Glados glados = new Glados("data/tasks.txt");
    private Stage stage;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setGlados(Glados glados, Stage stage) {
        this.glados = glados;
        this.stage = stage;
    }

    public void initGui() {
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(glados.getWelcomeMessage(), gladosImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {

        String input = userInput.getText();
        String response = glados.getResponse(input, stage);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, gladosImage));
        userInput.clear();
    }
}
