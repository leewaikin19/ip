import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            } else if (userInput.startsWith("todo ")) {
                userInput = userInput.replace("todo ", "");
                Task newItem = new Todo(userInput);
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
            } else if (userInput.startsWith("deadline ")) {
                userInput = userInput.replace("deadline ", "");
                Task newItem = new Deadline(userInput.split(" /by ")[0].stripTrailing(), 
                        userInput.split(" /by ")[1].stripTrailing());
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
            } else if (userInput.startsWith("event ")) {
                userInput = userInput.replace("event ", "");
                String description = "";
                String from = "";
                String to = "";
                String[] commands = userInput.split("/");
                for (int i = 0; i < commands.length; i++) {
                    if (commands[i].startsWith("from ")) {
                        from = commands[i].replace("from ", "").stripTrailing();
                    } else if (commands[i].startsWith("to ")) {
                        to = commands[i].replace("to ", "").stripTrailing();
                    } else {
                        description = commands[i].stripTrailing();
                    }
                }
                Task newItem = new Event(description, from, to);
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
            } else {
                items.add(new Task(userInput));
                System.out.println("added: " + userInput);
            }
        }
        scanner.close();
    }
}
