import java.time.LocalDateTime;

public class Event extends Task {
    protected String to;
    protected String from;
    protected LocalDateTime toDateTime;
    protected LocalDateTime fromDateTime;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    public Event(String description, LocalDateTime fromDateTime, LocalDateTime toDateTime, 
            String from, String to) {
        super(description);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        this.from = from;
        this.to = to;
    }
    
    public String toString() {
        return fromDateTime == null || toDateTime == null
                ? "[E]" + super.toString() + " (from: " + from + " to: " + to + ")"
                : "[E]" + super.toString() + " (from: " + fromDateTime + " to: " + toDateTime + ")";
    }
}
