package glados.tasks;

/** Simple Todo Task */

public class Todo extends Task {
    public Todo(String description) {
        super(description);
        assert description != null && !description.isBlank();
    }

    public String toString() {
        return "[T]" + super.toString();
    }
}
