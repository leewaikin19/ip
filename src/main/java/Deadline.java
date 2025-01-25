import java.time.LocalDateTime;

public class Deadline extends Task {
    protected String by;
    protected LocalDateTime byDateTime;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime byDateTime, String by) {
        super(description);
        this.byDateTime = byDateTime;
        this.by = by;
    }
    
    public String toString() {
        return by == null
                ? "[D]" + super.toString() + " (by: " + byDateTime + ")"
                : "[D]" + super.toString() + " (by: " + by + ")";
    }
}
