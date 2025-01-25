package glados.local;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import glados.exceptions.StorageException;
import glados.tasks.Deadline;
import glados.tasks.Event;
import glados.tasks.Task;
import glados.tasks.TaskList;
import glados.tasks.Todo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/** Class to handle Local Storage */
public class Storage {
    private Path path;

    public Storage(String filePath) {
        String home = System.getProperty("user.dir");
        path = Paths.get(home, "data.txt");
    }

    
    /** 
     * Saves task list to local data file
     * @param tasks task list to be saved
     */
    public void saveData(TaskList tasks) {
        try {
            String dataToSave = "";
            for (int i = 0; i < tasks.size(); i++) {
                dataToSave += tasks.get(i) + "\n";
            }
            Files.write(path, dataToSave.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.err.println("An error occurred while saving the file: " + e.getMessage());
        }
    }
    
    /** 
     * Parses local data file into a task list
     * @return ArrayList<Task> task list to be returned
     * @throws StorageException If data file contains lines that are corrupted
     */
    public ArrayList<Task> loadData() throws StorageException {
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            List<String> file = Files.readAllLines(path);
            for (String line : file) {
                Boolean isDone = false;
                if (line.startsWith("[T]")) {
                    line = line.replaceFirst("\\[T\\]", "");
                    isDone = line.startsWith("[X]");
                    line = line.substring(4);
                    Task task = new Todo(line);
                    task.setDone(isDone); 
                    taskList.add(task);
                } else if (line.startsWith("[D]")) {
                    line = line.replaceFirst("\\[D\\]", "");
                    isDone = line.startsWith("[X]");
                    line = line.substring(4);
                    String[] params = line.split("\\(by: ",2);
                    Task task = new Deadline(params[0].stripTrailing(), 
                    params[1].substring(0, params[1].length() - 1));
                    task.setDone(isDone); 
                    taskList.add(task);
                } else if (line.startsWith("[E]")) {
                    line = line.replaceFirst("\\[E\\]", "");
                    isDone = line.startsWith("[X]");
                    line = line.substring(4);
                    String[] params = line.split("\\(from: ",2);
                    String[] params2 = params[1].split(" to: ", 2);
                    Task task = new Event(params[0].stripTrailing(), 
                    params2[0], params2[1].substring(0, params2[1].length() - 1));
                    task.setDone(isDone); 
                    taskList.add(task);
                } else {
                    throw new StorageException("An error occured while parsing data");
                }
            }
        } catch (IOException e) {
            try {
                Files.createFile(path);
            } catch (IOException e2) {
                System.out.println("Error: An error occurred while creating data file: " + e2.getMessage());
            }
        }
        return taskList;
    }
}
