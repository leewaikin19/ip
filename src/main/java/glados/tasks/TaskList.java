package glados.tasks;

import java.util.ArrayList;

/** List of all current tasks */
public class TaskList {
    private ArrayList<Task> taskList;

    public TaskList() {
        taskList = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Gets the size of the task list.
     * 
     * @return int Size of the task list
     */

    public int size() {
        return taskList.size();
    }

    /**
     * Gets the ith task.
     * 
     * @param index Index
     * @return Task Task at index
     */
    public Task get(int index) {
        return taskList.get(index);
    }

    /**
     * Removes the ith task.
     * 
     * @param index Index
     */
    public void remove(int index) {
        taskList.remove(index);
    }

    /**
     * Adds a task to the list.
     * 
     * @param task Task to be added.
     */

    public void add(Task task) {
        taskList.add(task);
    }

    /**
     * Returns a task list with descriptions containing the query.
     * 
     * @param query Search query
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
