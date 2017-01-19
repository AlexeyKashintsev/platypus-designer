package com.eas.designer.explorer.project;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.core.IsEqual;

/**
 *
 * @author user
 */
public class CommandLineTest {

    @Test
    public void parseEmpty() {
        List<String> args = PlatypusProjectActions.parseArgs("");
        assertTrue(args.isEmpty());
    }

    @Test
    public void parseNull() {
        List<String> args = PlatypusProjectActions.parseArgs(null);
        assertTrue(args.isEmpty());
    }

    @Test
    public void left() {
        List<String> args = PlatypusProjectActions.parseArgs("\"a r g 1\" arg2 arg3");
        assertEquals(3, args.size());
        assertThat(args.get(0), new IsEqual("a r g 1"));
        assertThat(args.get(1), new IsEqual("arg2"));
        assertThat(args.get(2), new IsEqual("arg3"));
    }

    @Test
    public void right() {
        List<String> args = PlatypusProjectActions.parseArgs("arg1 arg2 \"a r g 3\"");
        assertEquals(3, args.size());
        assertThat(args.get(0), new IsEqual("arg1"));
        assertThat(args.get(1), new IsEqual("arg2"));
        assertThat(args.get(2), new IsEqual("a r g 3"));
    }

    @Test
    public void center() {
        List<String> args = PlatypusProjectActions.parseArgs("arg1 \"a r g 2\" arg3");
        assertEquals(3, args.size());
        assertThat(args.get(0), new IsEqual("arg1"));
        assertThat(args.get(1), new IsEqual("a r g 2"));
        assertThat(args.get(2), new IsEqual("arg3"));
    }
    
    @Test
    public void wrondEscape() {
        List<String> args = PlatypusProjectActions.parseArgs("arg1 \"a r g 2 arg3");
        assertEquals(6, args.size());
        assertThat(args.get(0), new IsEqual("arg1"));
        assertThat(args.get(1), new IsEqual("\"a"));
        assertThat(args.get(2), new IsEqual("r"));
        assertThat(args.get(3), new IsEqual("g"));
        assertThat(args.get(4), new IsEqual("2"));
        assertThat(args.get(5), new IsEqual("arg3"));
    }
}
