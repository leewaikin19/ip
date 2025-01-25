package glados.tasks;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> taskList;
    public TaskList() {
        taskList = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }
    public int size() {
        return taskList.size();
    }

    public Task get(int index) {
        return taskList.get(index);
    }

    public void remove(int index) {
        taskList.remove(index);
    }
    public void add(Task task) {
        taskList.add(task);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < taskList.size(); i++){
            result += (i + 1) + ". " + taskList.get(i) + "\n";
        }
        return result;
    }
}
