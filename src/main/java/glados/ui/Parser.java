package glados.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import glados.commands.AddTaskCommand;
import glados.commands.Command;
import glados.commands.RemoveTaskCommand;
import glados.commands.UpdateTaskCommand;
import glados.exceptions.ParserException;

/** Class to handle parsing of user inputs */
public class Parser {

    /**
     * Parses a string into a DateTime format. Returns null if string is not a date
     * or time.
     * 
     * @param str String to be parsed
     * @return LocalDateTime Parsed datetime
     */
    private static LocalDateTime parseDateTime(String str) {
        String[] formats = { "d/M/yyyy", "d/M/yyyy HHmm", "d/M/yyyy hh:mm a", "d/M/yyyy hh:mm:ss a", "yyyy/M/d",

                "yyyy/M/d", "yyyy/M/d hh:mm a", "yyyy/M/d hh:mm:ss a", "d-M-yyyy", "d-M-yyyy HHmm", "d-M-yyyy hh:mm a",

                "d-M-yyyy hh:mm:ss a", "yyyy-M-d", "yyyy-M-d", "yyyy-M-d hh:mm a", "yyyy-M-d hh:mm:ss a" };
        LocalDate date = null;
        LocalDateTime dateTime = null;
        for (String format : formats) {
            try {
                dateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(format));
                break;
            } catch (DateTimeParseException e) {
            }
            try {
                date = LocalDate.parse(str, DateTimeFormatter.ofPattern(format));
                break;
            } catch (DateTimeParseException e) {
            }
        }
        if (dateTime == null) {
            if (date == null) {
                return null;
            }
            dateTime = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
            dateTime = LocalDateTime.of(date, LocalTime.of(0, 0, 0));
        }
        return dateTime;
    }

    /**
     * Parses user mark command.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */
    private static Command parseMarkCommand(String command) throws ParserException {
        command = command.replaceFirst("mark ", "");
        int index = 0;
        try {
            index = Integer.parseInt(command) - 1;
        } catch (NumberFormatException e) {
            throw new ParserException("Only use numbers after mark!");
        }
        return new UpdateTaskCommand("mark", true, index, "Nice! I've marked this task as done:\n");
    }

    /**
     * Parses user unmark command.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */
    private static Command parseUnmarkCommand(String command) throws ParserException {
        command = command.replaceFirst("unmark ", "");
        int index = 0;
        try {
            index = Integer.parseInt(command) - 1;
        } catch (NumberFormatException e) {
            throw new ParserException("Only use numbers after unmark!");
        }
        return new UpdateTaskCommand("unmark", false, index, "OK, I've marked this task as not done yet:\n");
    }

    /**
     * Parses user delete command.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */
    private static Command parseDeleteCommand(String command) throws ParserException {
        command = command.replaceFirst("delete ", "");
        int index = 0;
        try {
            index = Integer.parseInt(command) - 1;
        } catch (NumberFormatException e) {
            throw new ParserException("Only use numbers after delete!");
        }
        return new RemoveTaskCommand("delete", index);
    }

    /**
     * Parses user todo command.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */
    private static Command parseTodoCommand(String command) throws ParserException {
        command = command.replaceFirst("todo ", "");
        if (command.isBlank()) {
            throw new ParserException("Todo description cannot be blank. Please try again.");
        }
        return new AddTaskCommand("todo", command);
    }

    /**
     * Parses user deadline command.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */
    private static Command parseDeadlineCommand(String command) throws ParserException {
        command = command.replaceFirst("deadline ", "");
        if (command.isBlank()) {
            throw new ParserException("Deadline description cannot be blank. Please try again.");
        }
        if (!command.contains("/by ")) {
            throw new ParserException("Deadline must contain /by field. Please try again.");
        }
        if (command.split("/by ")[1].stripTrailing().isBlank()) {
            throw new ParserException("Deadline /by field cannot be empty. Please try again.");
        }
        String[] params = command.split("/by ");

        LocalDateTime dateTime = parseDateTime(params[1]);
        if (dateTime == null) {
            return new AddTaskCommand("deadline", params[0].stripTrailing(), params[1]);
        }
        return new AddTaskCommand("deadline", params[0].stripTrailing(), dateTime);
    }

    /**
     * Parses user event command.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */
    private static Command parseEventCommand(String command) throws ParserException {
        command = command.replaceFirst("event ", "");
        String description, from, to = "";
        int fromIndex = command.indexOf("/from");
        int toIndex = command.indexOf("/to");
        if (fromIndex == -1 || toIndex == -1) {
            throw new ParserException("Event must contain both /from field and /to field. Please try again.");
        }
        if (fromIndex == 0 || toIndex == 0) {
            throw new ParserException("Event description cannot be blank. Please try again.");
        }
        if (fromIndex < toIndex) {
            description = command.substring(0, fromIndex - 1).stripTrailing();
            from = command.substring(fromIndex + 5, toIndex - 1).stripTrailing().stripLeading();
            to = command.substring(toIndex + 3).stripTrailing().stripLeading();
        } else {
            description = command.substring(0, toIndex - 1).stripTrailing();
            to = command.substring(toIndex + 3, fromIndex - 1).stripTrailing().stripLeading();
            from = command.substring(fromIndex + 5).stripTrailing().stripLeading();
        }
        if (from.isBlank()) {
            throw new ParserException("Event /from field cannot be empty. Please try again.");
        }
        if (to.isBlank()) {
            throw new ParserException("Event /to field cannot be empty. Please try again.");
        }
        LocalDateTime fromDateTime = parseDateTime(from);
        LocalDateTime toDateTime = parseDateTime(to);
        if (fromDateTime == null || toDateTime == null) {
            return new AddTaskCommand("event", description, from, to);
        }
        return new AddTaskCommand("event", description, fromDateTime, toDateTime);
    }

    /**
     * Parses user inputs by commands.
     * 
     * @param command String to be parsed
     * @return Command Parsed command
     * @throws ParserException If command is not valid
     */

    public static Command parse(String command) throws ParserException {
        if (command == null) {
            return new Command("");
        }
        if (command.equals("bye")) {
            return new Command("exit");
        } else if (command.equals("list")) {
            return new Command("list");
        } else if (command.equals("help")) {
            return new Command("help");
        } else if (command.startsWith("find ")) {
            command = command.replaceFirst("find ", "");
            return new Command("find", command);
        } else if (command.startsWith("mark ")) {
            return parseMarkCommand(command);
        } else if (command.startsWith("unmark ")) {
            return parseUnmarkCommand(command);
        } else if (command.startsWith("delete ")) {
            return parseDeleteCommand(command);
        } else if (command.startsWith("todo ")) {
            return parseTodoCommand(command);
        } else if (command.startsWith("deadline ")) {
            return parseDeadlineCommand(command);
        } else if (command.startsWith("event ")) {
            return parseEventCommand(command);
        } else {
            System.out.println("Unknown command. Please try again.");
        }
        return new Command(command);
    }
}
