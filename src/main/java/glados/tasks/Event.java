package glados.tasks;

import java.time.LocalDateTime;

/** Event Task that must have a to and from field. */
public class Event extends Task {
    protected String to;
    protected String from;
    protected LocalDateTime toDateTime;
    protected LocalDateTime fromDateTime;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        assert description != null && !description.isBlank();
        assert from != null && !from.isBlank();
        assert to != null && !to.isBlank();
    }

    public Event(String description, LocalDateTime fromDateTime, LocalDateTime toDateTime,
            String from, String to) {
        super(description);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.from = from;
        this.to = to;
        assert description != null && !description.isBlank();
        assert from != null && !from.isBlank();
        assert to != null && !to.isBlank();
        assert fromDateTime != null;
        assert toDateTime != null;
    }

    public String toString() {
        return fromDateTime == null || toDateTime == null
                ? "[E]" + super.toString() + " (from: " + from + " to: " + to + ")"
                : "[E]" + super.toString() + " (from: " + fromDateTime + " to: " + toDateTime + ")";
    }
}
