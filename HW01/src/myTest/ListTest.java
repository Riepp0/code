package myTest;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

public class ListTest {
    private ListAdapter l;
    private Object o01;
    private Object o02;
    private Object o03;

    @Before
    public void before() {
        // Creating empty ListAdapter
        l = new ListAdapter();

        // Initializing Objects
        o01 = new Object();
        o02 = new Object();
        o03 = new Object();
    }

    @Test
    public void list_add() {

        assertEquals("Failed : list didn't add, o01", true, l.add(o01));
        assertEquals("Failed : list didn't add, o03", true, l.add(o03));
        l.add(1, o02);

        assertEquals("Failed : object first position", o01, l.get(0));
        assertEquals("Failed : object second position", o02, l.get(1));
        assertEquals("Failed : object third position", o03, l.get(2));

        assertThrows("Failed : exception 1 not thrown", NullPointerException.class, () -> l.add(null));
        assertThrows("Failed : exception 2 not thrown", NullPointerException.class, () -> l.add(0, null));
        assertThrows("Failed : exception 3 not thrown", IndexOutOfBoundsException.class, () -> l.add(-1, new Object()));
    }

    @Test
    public void list_addAll() {
        HList c = new ListAdapter();
        c.add(o01);
        c.add(o02);
        assertTrue("Failed : list didn't add, c", l.addAll(c));
        assertEquals("Failed : list size is not 2",2, l.size());
        assertEquals("Failed : object first position", o01, l.get(0));
        assertEquals("Failed : object second position", o02, l.get(1));

        assertTrue("Failed : list didn't add, c", l.addAll(1, c));
        assertEquals("Failed : object first position", o01, l.get(0));
        assertEquals("Failed : object second position", o01, l.get(1));
        assertEquals("Failed : object third position", o02, l.get(2));
        assertEquals("Failed : object fourth position", o02, l.get(3));

        assertThrows("Failed : exception 1 not thrown", NullPointerException.class, () -> l.addAll(null));
        assertThrows("Failed : exception 2 not thrown", NullPointerException.class, () -> l.addAll(0, null));
        assertThrows("Failed : exception 3 not thrown", IndexOutOfBoundsException.class, () -> l.addAll(-1, c));

        HList n = new ListAdapter();
        assertFalse("Failed : added", l.addAll(n));
        assertFalse("Failed : added", l.addAll(1, n));
    }

    @Test
    public void list_clear() {
        l.add(o01);
        l.clear();
        assertEquals("Failed : not empty", 0, l.size());
    }

    @Test
    public void list_contains() {
        l.add(o01);
        l.add(o02);
        assertTrue("Failed : doesn't contain", l.contains(o02));
        assertFalse("Failed : contains", l.contains(o03));

        assertThrows("Failed : exception not thrown", NullPointerException.class, () -> l.contains(null));
    }

    @Test
    public void list_containsAll() {
        HList c = new ListAdapter();
        c.add(o01);
        c.add(o02);
        l.add(o02);
        l.add(o03);
        l.add(o01);
        l.add(o03);
        assertTrue("Failed : doesn't contain", l.containsAll(c));

        Object o04 = new Object();
        HList n = new ListAdapter();
        n.add(o04);
        assertFalse("Failed : contains", l.containsAll(n));

        assertThrows("Failed : exception 1 not thrown", NullPointerException.class, () -> l.containsAll(null));
    }

    @Test
    public void list_equals() {
        l.add(o01);
        l.add(o02);
        HList c = new ListAdapter();
        c.addAll(l);
        assertTrue("Failed : not equals", l.equals(c));
    }

    @Test
    public void list_get() {
        l.add(o01);
        l.add(0, o02);

        assertEquals("Failed : wrong position o02", o02, l.get(0));
        assertEquals("Failed : wrong position o01", o01, l.get(1));

        assertThrows("Failed : exception not thrown", IndexOutOfBoundsException.class, () -> l.get(-1));
    }

    @Test
    public void list_indexOf() {
        l.add(o01);
        l.add(o02);

        assertEquals("Failed : wrong position o01", 0, l.indexOf(o01));
        assertEquals("Failed : wrong position o02", 1, l.indexOf(o02));
        assertEquals("Failed : wrong position o03", -1, l.indexOf(o03));

        assertThrows("Failed : exception not thrown", NullPointerException.class, () -> l.indexOf(null));
    }

    @Test
    public void list_isEmpty() {
        assertTrue("Failed : isn't empty", l.isEmpty());
    }

    @Test
    public void list_iterator() {

    }

    @Test
    public void list_lastIndexOf() {
        l.add(o01);
        l.add(o02);
        l.add(o01);
        l.add(o02);

        assertEquals("Failed : wrong position o01", 2, l.lastIndexOf(o01));
        assertEquals("Failed : wrong position o01", 3, l.lastIndexOf(o02));

        assertThrows("Failed : exception not thrown", NullPointerException.class, () -> l.lastIndexOf(null));
    }

    @Test
    public void list_listIterator() {

    }

    @Test
    public void list_remove() {
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o01);
        l.add(o02);
        l.add(o03);

        assertEquals("Failed : wrong position o01", o01, l.remove(0));
        assertEquals("Failed : wrong position o02", o02, l.remove(3));
        assertEquals("Failed : wrong position o03", o03, l.remove(6));

        assertTrue("Failed : no such element, o01", l.remove(o01));
        assertTrue("Failed : no such element, o02", l.remove(o02));
        assertTrue("Failed : no such element, o03", l.remove(o03));

        assertEquals("Failed : size check", 3, l.size());
        Object o04 = new Object();
        assertFalse("Failed : o04 was in the list", l.remove(o04));

        assertThrows("Failed : exception 1 not thrown", IndexOutOfBoundsException.class, () -> l.remove(-1));
        assertThrows("Failed : exception 2 not thrown", NullPointerException.class, () -> l.remove(null));
    }

    @Test
    public void list_removeAll() {
        HList c = new ListAdapter();
        c.add(o01);
        c.add(o02);
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o02);
        l.add(o01);

        assertTrue("Failed : list not modified", l.removeAll(c));
        assertEquals("Failed : size check", 1, l.size());

        assertThrows("Failed : exception not thrown", NullPointerException.class, () -> l.removeAll(null));
    }

    @Test
    public void list_reteainAll() {
        HList c = new ListAdapter();
        c.add(o01);
        c.add(o02);
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o02);
        l.add(o01);

        assertTrue("Failed : list not modified", l.retainAll(c));
        assertEquals("Failed : size check", 4, l.size());

        assertThrows("Failed : exception not thrown", NullPointerException.class, () -> l.retainAll(null));
    }

    @Test
    public void list_set() {
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o03);

        assertEquals("Failed : replaced wrong one", o03, l.set(2, o02));
        assertEquals("Failed : o02 not replaced correctly", o02, l.get(2));

        assertThrows("Failed : exception 1 not thrown", NullPointerException.class, () -> l.set(1, null));
        assertThrows("Failed : exception 2 not thrown", IndexOutOfBoundsException.class, () -> l.set(-1, o01));
    }

    @Test
    public void list_size() {
        l.add(o01);
        l.add(o02);
        l.add(o03);

        assertEquals("Failed: wrong size calculation", 3, l.size());
    }

    @Test
    public void list_subList() {

    }

    @Test
    public void list_toArray() {
        l.add(o01);
        l.add(o02);
        l.add(o03);

        assertArrayEquals("Failed : wrong copy 1", new Object[] { o01, o02, o03 }, l.toArray());
        assertArrayEquals("Failed : wrong copy 2", new Object[] { o01, o02, o03, null }, l.toArray(new Object[4]));
        assertArrayEquals("Failed : didn't resize", new Object[] { o01, o02, o03 }, l.toArray(new Object[1]));

        assertThrows("Failed : exception not thrown", NullPointerException.class, () -> l.toArray(null));
    }
}
