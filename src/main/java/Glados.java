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
                userInput = userInput.replaceFirst("mark ", "");
                int index = 0;
                try {
                    index = Integer.parseInt(userInput) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Only use numbers after mark!");
                    continue;
                }
                Task targetTask = items.get(index);
                targetTask.isDone = true;
                System.out.println("Nice! I've marked this task as done:\n" 
                        + targetTask);
            } else if (userInput.startsWith("unmark ")) {
                userInput = userInput.replaceFirst("unmark ", "");
                int index = 0;
                try {
                    index = Integer.parseInt(userInput) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Only use numbers after mark!");
                    continue;
                }
                Task targetTask = items.get(index);
                targetTask.isDone = false;
                System.out.println("OK, I've marked this task as not done yet:\n" 
                        + targetTask);
            } else if (userInput.startsWith("todo ")) {
                userInput = userInput.replaceFirst("todo ", "");
                if (userInput.isBlank()) {
                    System.out.println("Todo description cannot be blank. Please try again.");
                    continue;
                }
                Task newItem = new Todo(userInput);
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
            } else if (userInput.startsWith("deadline ")) {
                userInput = userInput.replaceFirst("deadline ", "");
                if (userInput.isBlank()) {
                    System.out.println("Deadline description cannot be blank. Please try again.");
                    continue;
                }
                if (!userInput.contains(" /by ")) {
                    System.out.println("Deadline must contain /by field. Please try again.");
                    continue;
                }
                if (userInput.split(" /by ")[1].stripTrailing().isBlank()) {
                    System.out.println("Deadline /by field cannot be empty. Please try again.");
                    continue;
                }
                Task newItem = new Deadline(userInput.split(" /by ")[0].stripTrailing(), 
                        userInput.split(" /by ")[1].stripTrailing());
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
            } else if (userInput.startsWith("event ")) {
                userInput = userInput.replaceFirst("event ", "");
                String description = "";
                String from = "";
                String to = "";
                String[] commands = userInput.split("/");
                for (int i = 0; i < commands.length; i++) {
                    if (commands[i].startsWith("from ")) {
                        from = commands[i].replaceFirst("from ", "").stripTrailing();
                    } else if (commands[i].startsWith("to ")) {
                        to = commands[i].replaceFirst("to ", "").stripTrailing();
                    } else {
                        description = commands[i].stripTrailing();
                    }
                }
                if (userInput.isBlank()) {
                    System.out.println("Event description cannot be blank. Please try again.");
                    continue;
                }
                if (!userInput.contains(" /from ")) {
                    System.out.println("Event must contain /from field. Please try again.");
                    continue;
                }
                if (userInput.split(" /from ")[1].stripTrailing().isBlank()) {
                    System.out.println("Event /from field cannot be empty. Please try again.");
                    continue;
                }
                if (!userInput.contains(" /to ")) {
                    System.out.println("Event must contain /to field. Please try again.");
                    continue;
                }
                if (userInput.split(" /to ")[1].stripTrailing().isBlank()) {
                    System.out.println("Event /to field cannot be empty. Please try again.");
                    continue;
                }
                Task newItem = new Event(description, from, to);
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        }
        scanner.close();
    }
}
