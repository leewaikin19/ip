package glados.tasks;

import java.time.LocalDateTime;

/** Deadline Task that must have a by field */
public class Deadline extends Task {
    protected String by;
    protected LocalDateTime byDateTime;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        assert description != null && !description.isBlank();
        assert by != null && !by.isBlank();
    }

    public Deadline(String description, LocalDateTime byDateTime, String by) {
        super(description);
        this.byDateTime = byDateTime;
        this.by = by;
        assert description != null && !description.isBlank();
        assert by != null && !by.isBlank();
        assert byDateTime != null;
    }

    public String toString() {
        return by == null
                ? "[D]" + super.toString() + " (by: " + byDateTime + ")"
                : "[D]" + super.toString() + " (by: " + by + ")";
    }
}
