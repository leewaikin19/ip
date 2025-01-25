import java.util.Scanner;

import java.util.List;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Glados {
    private static ArrayList<Task> items = new ArrayList<Task>();
    private static String home = System.getProperty("user.dir");
    
    private static Path path = Paths.get(home, "data.txt");

    private static void parseData(List<String> file) {
        for (int i = 0; i < file.size(); i++) {
            String line = file.get(i);
            Boolean isDone = false;
            if (line.startsWith("[T]")) {
                line = line.replaceFirst("\\[T\\]", "");
                isDone = line.startsWith("[X]");
                line = line.substring(4);
                Task task = new Todo(line);
                task.isDone = isDone;
                items.add(task);
            } else if (line.startsWith("[D]")) {
                line = line.replaceFirst("\\[D\\]", "");
                isDone = line.startsWith("[X]");
                line = line.substring(4);
                String[] params = line.split("\\(by: ",2);
                Task task = new Deadline(params[0].stripTrailing(), 
                params[1].substring(0, params[1].length() - 1));
                task.isDone = isDone;
                items.add(task);
            } else if (line.startsWith("[E]")) {
                line = line.replaceFirst("\\[E\\]", "");
                isDone = line.startsWith("[X]");
                line = line.substring(4);
                String[] params = line.split("\\(from: ",2);
                String[] params2 = params[1].split(" to: ", 2);
                Task task = new Event(params[0].stripTrailing(), 
                params2[0], params2[1].substring(0, params2[1].length() - 1));
                task.isDone = isDone;
                items.add(task);
            } else {
                System.out.println("An error occured while parsing data");
                return;
            }
        }
    }
    private static void saveData(ArrayList<Task> items) {
        try {
            String dataToSave = "";
            for (int i = 0; i < items.size(); i++) {
                dataToSave += items.get(i) + "\n";
            }
            Files.write(path, dataToSave.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        try {
            parseData(Files.readAllLines(path));
        }
        catch (IOException e) {
            try {
                Files.createFile(path);
            } catch (IOException e2) {
                System.out.println("Error: An error occurred while creating data file: " + e2.getMessage());
                return ;
            }
        }
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
                saveData(items);
            } else if (userInput.startsWith("unmark ")) {
                userInput = userInput.replaceFirst("unmark ", "");
                int index = 0;
                try {
                    index = Integer.parseInt(userInput) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Only use numbers after unmark!");
                    continue;
                }
                Task targetTask = items.get(index);
                targetTask.isDone = false;
                System.out.println("OK, I've marked this task as not done yet:\n" 
                        + targetTask);
                saveData(items);
            } else if (userInput.startsWith("delete ")) {
                userInput = userInput.replaceFirst("delete ", "");
                int index = 0;
                try {
                    index = Integer.parseInt(userInput) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Only use numbers after delete!");
                    continue;
                }
                Task targetTask = items.get(index);
                items.remove(index);
                System.out.println("Got it. I've removed this task:\n" + targetTask
                        + "\nNow you have " + items.size() + " tasks in the list.");
                saveData(items);
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
                saveData(items);
            } else if (userInput.startsWith("deadline ")) {
                userInput = userInput.replaceFirst("deadline ", "");
                System.out.println(userInput);
                if (userInput.isBlank()) {
                    System.out.println("Deadline description cannot be blank. Please try again.");
                    continue;
                }
                if (!userInput.contains("/by ")) {
                    System.out.println("Deadline must contain /by field. Please try again.");
                    continue;
                }
                if (userInput.split("/by ")[1].stripTrailing().isBlank()) {
                    System.out.println("Deadline /by field cannot be empty. Please try again.");
                    continue;
                }
                String[] params = userInput.split("/by ");
                String[] formats = {"d/M/yyyy", "d/M/yyyy HHmm", "d/M/yyyy hh:mm a", "d/M/yyyy hh:mm:ss a", "yyyy/M/d", "yyyy/M/d", "yyyy/M/d hh:mm a", "yyyy/M/d hh:mm:ss a", "d-M-yyyy", "d-M-yyyy HHmm", "d-M-yyyy hh:mm a", "d-M-yyyy hh:mm:ss a", "yyyy-M-d", "yyyy-M-d", "yyyy-M-d hh:mm a", "yyyy-M-d hh:mm:ss a"};
                Boolean isDate = false;
                Boolean isDateTime = false;
                LocalDate date = null;
                LocalDateTime dateTime = null;
                for (String format: formats) {
                    try {
                        dateTime = LocalDateTime.parse(params[1], DateTimeFormatter.ofPattern(format));
                        isDateTime = true;
                        break;
                    } catch (DateTimeParseException e) {
                        //System.out.println(e.getMessage());
                    }
                    try {
                        date = LocalDate.parse(params[1], DateTimeFormatter.ofPattern(format));
                        isDate = true;
                        break;
                    } catch (DateTimeParseException e) {
                        //System.out.println(e.getMessage());
                    }
                }
                Task newItem = null;
                if (isDate) {
                    newItem = new Deadline(params[0].stripTrailing(), date.toString());
                } else if (isDateTime) {
                    newItem = new Deadline(params[0].stripTrailing(), dateTime.toString());
                } else {
                    newItem = new Deadline(params[0].stripTrailing(), 
                       params[1].stripTrailing());
                }
                
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
                saveData(items);
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
                if (!userInput.contains("/from ")) {
                    System.out.println("Event must contain /from field. Please try again.");
                    continue;
                }
                if (userInput.split("/from ")[1].stripTrailing().isBlank()) {
                    System.out.println("Event /from field cannot be empty. Please try again.");
                    continue;
                }
                if (!userInput.contains("/to ")) {
                    System.out.println("Event must contain /to field. Please try again.");
                    continue;
                }
                if (userInput.split("/to ")[1].stripTrailing().isBlank()) {
                    System.out.println("Event /to field cannot be empty. Please try again.");
                    continue;
                }
                Task newItem = new Event(description, from, to);
                items.add(newItem);
                System.out.println("Got it. I've added this task:\n" + newItem 
                        + "\nNow you have " + items.size() + " tasks in the list.");
                saveData(items);
            } else {
                System.out.println("Unknown command. Please try again.");
            }
        }
        scanner.close();
    }
}
