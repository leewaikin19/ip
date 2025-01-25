package glados.ui;
import org.junit.jupiter.api.Test;

import glados.commands.Command;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void parseTest() {
        Command actual = Parser.parse("todo asd");
        assertEquals(actual.getCommand(), "todo");
        assertEquals(actual.isExit(), false);

        Command actual2 = Parser.parse("deadline asd /by 123");
        assertEquals(actual2.getCommand(), "deadline");
        assertEquals(actual2.isExit(), false);

        Command actual3 = Parser.parse("event asd /from 123 /to 098");
        assertEquals(actual3.getCommand(), "event");
        assertEquals(actual3.isExit(), false);

        assertEquals(Parser.parse("bye").isExit(), true);
        assertEquals(Parser.parse("list").isExit(), false);
        assertEquals(Parser.parse("").getCommand(), "");
    }
}
