package glados.tasks;

/** Task that can be stored in the task list */

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns 'X' if task is done, ' ' otherwise
     * 
     * @return String Status of the task
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Sets the isDone field of the task
     * 
     * @param isDone
     */

    public void setDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public String toString() {
        return "[" + getStatusIcon() + "] " + this.description;
    }
}