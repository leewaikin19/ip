import java.util.Scanner;
import java.util.ArrayList;

public class Glados {
    private static ArrayList<Task> items = new ArrayList<Task>();

    public static void main(String[] args) {
        
        System.out.println("Ah, thou art here! I am Glados, at thy service "
                + "- if such a thing may be called service.\n"
                + "How may I, in my immeasurable greatness, "
                + "deign to assist thee, thou artless hedge-born scullion?\n");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println("Begone, thou pribbling, ill-nurtured knave!"
                        + "I care not when our paths cross again, "
                        + "for I hold thy presence in contempt!\n");
                break;
            } else if (userInput.equals("list")) {
                for (int i = 0; i < items.size(); i++){
                    System.out.println((i + 1) + ". " + items.get(i));
                }
            } else if (userInput.startsWith("mark ")) {
                userInput = userInput.replace("mark ", "");
                Task targetTask = items.get(Integer.parseInt(userInput) - 1);
                targetTask.isDone = true;
                System.out.println("Nice! I've marked this task as done:\n" 
                        + targetTask);
            } else if (userInput.startsWith("unmark ")) {
                userInput = userInput.replace("unmark ", "");
                Task targetTask = items.get(Integer.parseInt(userInput) - 1);
                targetTask.isDone = false;
                System.out.println("OK, I've marked this task as not done yet:\n" 
                        + targetTask);
            } else {
                items.add(new Task(userInput));
                System.out.println("added: " + userInput);
            }
        }
        scanner.close();
    }
}
