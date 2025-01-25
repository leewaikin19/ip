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

    /**
     * Returns a task list with descriptions containing the query
     * 
     * @param query search query
     * @return TaskList list of tasks that matches query
     */
    public TaskList find(String query) {
        ArrayList<Task> result = new ArrayList<>();
        for (Task task : taskList) {
            if (task.description.contains(query)) {
                result.add(task);
            }
        }
        return new TaskList(result);
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < taskList.size(); i++) {
            result += (i + 1) + ". " + taskList.get(i) + "\n";
        }
        return result;
    }
}
