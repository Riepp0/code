package test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import app.*;

public class GreetingTests {
    @Test
    public void testGreeting() {
        assertEquals("hello!", new Greeting().getGreeting());
    }
}
