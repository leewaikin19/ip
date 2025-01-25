package glados.ui;

import java.util.Scanner;

/** Class to handle interactions with the user */
public class Ui {
    private Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public void close() {
        scanner.close();
    }

    public static void showLoadingError() {
        System.out.println("Save file not found. Creating new file...");
    }

    public static void showWelcomeMessage() {
        System.out.println("Ah, thou art here! I am Glados, at thy service "
                + "- if such a thing may be called service.\n"
                + "How may I, in my immeasurable greatness, "
                + "deign to assist thee, thou artless hedge-born scullion?\n");
    }

    public static void showExitMessage() {
        System.out.println("Begone, thou pribbling, ill-nurtured knave!"
                + "I care not when our paths cross again, "
                + "for I hold thy presence in contempt!\n");
    }

    /**
     * Returns the next user input line, if exists.
     * 
     * @return String User input.
     */
    public String readLine() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }

    /**
     * Shows a message to the user
     * 
     * @param message
     */

    public static void show(String message) {
        System.out.println(message);
    }
}
