package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import myAdapter.*;

public class ListTest {
    @Test
    public void lt01_add() {
        // Creating empty ListAdapter
        ListAdapter l = new ListAdapter();

        // Initializing Objects
        Object o01 = new Object();
        Object o02 = new Object();
        Object o03 = new Object();

        // Expecting : o01 - o02 - o03
        l.add(o01);
        l.add(o03);
        l.add(1, o02);

        // Test
        assertEquals("Failed : object first position", o01, l.get(0));
        assertEquals("Failed : object second position", o02, l.get(1));
        assertEquals("Failed : object third position", o03, l.get(2));

        // Exception tests
        assertThrows(NullPointerException.class, () -> l.add(null));
        assertThrows(NullPointerException.class, () -> l.add(0, null));
        assertThrows(IndexOutOfBoundsException.class, () -> l.add(-1, new Object()));
    }

    @Test
    public void lt01_addAll() {
        // Creating empty ListAdapter
        ListAdapter l = new ListAdapter();

        //Initializing objects
    }
}
