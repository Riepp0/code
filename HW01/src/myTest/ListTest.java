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

        assertEquals("Errore : list didn't add, o01", true, l.add(o01));
        assertEquals("Errore : list didn't add, o03", true, l.add(o03));
        l.add(1, o02);

        assertEquals("Errore : object first position", o01, l.get(0));
        assertEquals("Errore : object second position", o02, l.get(1));
        assertEquals("Errore : object third position", o03, l.get(2));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> l.add(null));
        assertThrows("Errore : exception 2 not thrown", NullPointerException.class, () -> l.add(0, null));
        assertThrows("Errore : exception 3 not thrown", IndexOutOfBoundsException.class, () -> l.add(-1, new Object()));
    }

    @Test
    public void list_addAll() {
        HList c = new ListAdapter();
        c.add(o01);
        c.add(o02);
        assertTrue("Errore : list didn't add, c", l.addAll(c));
        assertEquals("Errore : list size is not 2", 2, l.size());
        assertEquals("Errore : object first position", o01, l.get(0));
        assertEquals("Errore : object second position", o02, l.get(1));

        assertTrue("Errore : list didn't add, c", l.addAll(1, c));
        assertEquals("Errore : object first position", o01, l.get(0));
        assertEquals("Errore : object second position", o01, l.get(1));
        assertEquals("Errore : object third position", o02, l.get(2));
        assertEquals("Errore : object fourth position", o02, l.get(3));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> l.addAll(null));
        assertThrows("Errore : exception 2 not thrown", NullPointerException.class, () -> l.addAll(0, null));
        assertThrows("Errore : exception 3 not thrown", IndexOutOfBoundsException.class, () -> l.addAll(-1, c));

        HList n = new ListAdapter();
        assertFalse("Errore : added", l.addAll(n));
        assertFalse("Errore : added", l.addAll(1, n));
    }

    @Test
    public void list_clear() {
        l.add(o01);
        l.clear();
        assertEquals("Errore : not empty", 0, l.size());
    }

    @Test
    public void list_contains() {
        l.add(o01);
        l.add(o02);
        assertTrue("Errore : doesn't contain", l.contains(o02));
        assertFalse("Errore : contains", l.contains(o03));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> l.contains(null));
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
        assertTrue("Errore : doesn't contain", l.containsAll(c));

        Object o04 = new Object();
        HList n = new ListAdapter();
        n.add(o04);
        assertFalse("Errore : contains", l.containsAll(n));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> l.containsAll(null));
    }

    @Test
    public void list_equals() {
        l.add(o01);
        l.add(o02);
        HList c = new ListAdapter();
        c.addAll(l);
        assertTrue("Errore : not equals", l.equals(c));
    }

    @Test
    public void list_get() {
        l.add(o01);
        l.add(0, o02);

        assertEquals("Errore : wrong position o02", o02, l.get(0));
        assertEquals("Errore : wrong position o01", o01, l.get(1));

        assertThrows("Errore : exception not thrown", IndexOutOfBoundsException.class, () -> l.get(-1));
    }

    @Test
    public void list_indexOf() {
        l.add(o01);
        l.add(o02);

        assertEquals("Errore : wrong position o01", 0, l.indexOf(o01));
        assertEquals("Errore : wrong position o02", 1, l.indexOf(o02));
        assertEquals("Errore : wrong position o03", -1, l.indexOf(o03));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> l.indexOf(null));
    }

    @Test
    public void list_isEmpty() {
        assertTrue("Errore : isn't empty", l.isEmpty());
    }

    @Test
    public void list_iterator() {
        HIterator iter = l.iterator();
        Object[] oa1 = new Object[5];

        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o02);
        l.add(o01);
        for (int i = 0; iter.hasNext(); i++) {
            oa1[i] = iter.next();
        }

        assertArrayEquals("Errore : iteration didn't work", new Object[] { o01, o02, o03, o02, o01 }, oa1);
        iter.remove();
        assertFalse("Errore : this doesn't have next", iter.hasNext());
        assertEquals("Errore : the size was not 4", 4, l.size());

        assertThrows("Errore : exception not thrown", IllegalStateException.class, () -> iter.remove());

    }

    @Test
    public void list_lastIndexOf() {
        l.add(o01);
        l.add(o02);
        l.add(o01);
        l.add(o02);

        assertEquals("Errore : wrong position o01", 2, l.lastIndexOf(o01));
        assertEquals("Errore : wrong position o01", 3, l.lastIndexOf(o02));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> l.lastIndexOf(null));
    }

    @Test
    public void list_listIterator() {
        HListIterator iter = l.listIterator();

        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o02);
        l.add(o01);
        iter.next();
        iter.next();
        iter.next();
        iter.add(o01);
        assertEquals("Errore : the size was not 6", 6, l.size());
        assertEquals("Errore : didn't get added at index 3", o01, l.get(3));
        assertTrue("Errore : this has no previous", iter.hasPrevious());
        assertEquals("Errore : iterator in the wrong position", o01, iter.previous());
        iter.remove();
        assertEquals("Errore : the size was not 5", 5, l.size());
        assertEquals("", 2, iter.previousIndex());
        assertEquals("", 3, iter.nextIndex());
        assertThrows("Errore : exception not thrown", IllegalStateException.class, () -> iter.remove());
        assertEquals("", o02, iter.next());
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

        assertEquals("Errore : wrong position o01", o01, l.remove(0));
        assertEquals("Errore : wrong position o02", o02, l.remove(3));
        assertEquals("Errore : wrong position o03", o03, l.remove(6));

        assertTrue("Errore : no such element, o01", l.remove(o01));
        assertTrue("Errore : no such element, o02", l.remove(o02));
        assertTrue("Errore : no such element, o03", l.remove(o03));

        assertEquals("Errore : size check", 3, l.size());
        Object o04 = new Object();
        assertFalse("Errore : o04 was in the list", l.remove(o04));

        assertThrows("Errore : exception 1 not thrown", IndexOutOfBoundsException.class, () -> l.remove(-1));
        assertThrows("Errore : exception 2 not thrown", NullPointerException.class, () -> l.remove(null));
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

        assertTrue("Errore : list not modified", l.removeAll(c));
        assertEquals("Errore : size check", 3, l.size());

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> l.removeAll(null));
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

        assertTrue("Errore : list not modified", l.retainAll(c));
        assertEquals("Errore : size check", 4, l.size());

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> l.retainAll(null));
    }

    @Test
    public void list_set() {
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o03);

        assertEquals("Errore : replaced wrong one", o03, l.set(2, o02));
        assertEquals("Errore : o02 not replaced correctly", o02, l.get(2));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> l.set(1, null));
        assertThrows("Errore : exception 2 not thrown", IndexOutOfBoundsException.class, () -> l.set(-1, o01));
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
        Object o01 = new Object();
        Object o02 = new Object();
        Object o03 = new Object();
        Object o04 = new Object();
        Object o05 = new Object();
        Object o06 = new Object();
        Object NO = new Object();

        l.add(NO);
        l.add(NO);
        l.add(o01);
        l.add(o02);
        l.add(o03);
        l.add(o04);
        l.add(o05);
        l.add(o06);
        l.add(NO);
        l.add(NO);

        HList sub = l.subList(2, 8);
        Object[] oa1 = new Object[6];
        assertArrayEquals("Errore : sublist creation broken", new Object[] { o01, o02, o03, o04, o05, o06 }, sub.toArray());
        HListIterator iter = sub.listIterator();
        for (int i = 0; iter.hasNext(); i++) {
            oa1[i] = iter.next();
        }
        assertArrayEquals("Errore : iterator creation broken", new Object[] { o01, o02, o03, o04, o05, o06 }, oa1);

        sub.clear();

        //add
        assertEquals("Errore : sub didn't add, o01", true, sub.add(o01));
        assertEquals("Errore : sub didn't add, o03", true, sub.add(o03));
        sub.add(1, o02);

        assertEquals("Errore : object first position", o01, sub.get(0));
        assertEquals("Errore : object second position", o02, sub.get(1));
        assertEquals("Errore : object third position", o03, sub.get(2));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> sub.add(null));
        assertThrows("Errore : exception 2 not thrown", NullPointerException.class, () -> sub.add(0, null));
        assertThrows("Errore : exception 3 not thrown", IndexOutOfBoundsException.class, () -> sub.add(-1, new Object()));

        sub.clear();

        //addall
        HList c = new ListAdapter();
        c.add(o01);
        c.add(o02);
        assertTrue("Errore : sublist didn't add, c", sub.addAll(c));
        assertEquals("Errore : sublist size is not 2", 2, sub.size());
        assertEquals("Errore : object first position", o01, sub.get(0));
        assertEquals("Errore : object second position", o02, sub.get(1));

        assertTrue("Errore : sublist didn't add, c", sub.addAll(1, c));
        assertEquals("Errore : object first position", o01, sub.get(0));
        assertEquals("Errore : object second position", o01, sub.get(1));
        assertEquals("Errore : object third position", o02, sub.get(2));
        assertEquals("Errore : object fourth position", o02, sub.get(3));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> sub.addAll(null));
        assertThrows("Errore : exception 2 not thrown", NullPointerException.class, () -> sub.addAll(0, null));
        assertThrows("Errore : exception 3 not thrown", IndexOutOfBoundsException.class, () -> sub.addAll(-1, c));

        HList n = new ListAdapter();
        assertFalse("Errore : added", sub.addAll(n));
        assertFalse("Errore : added", sub.addAll(1, n));

        sub.clear();

        //clear
        sub.add(o01);
        sub.clear();
        assertEquals("Errore : not empty", 0, sub.size());

        sub.clear();

        //contains
        sub.add(o01);
        sub.add(o02);
        assertTrue("Errore : doesn't contain", sub.contains(o02));
        assertFalse("Errore : contains", sub.contains(o03));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> sub.contains(null));

        sub.clear();

        //containsall
        HList c1 = new ListAdapter();
        c1.add(o01);
        c1.add(o02);
        sub.add(o02);
        sub.add(o03);
        sub.add(o01);
        sub.add(o03);
        assertTrue("Errore : doesn't contain", sub.containsAll(c1));

        Object o07 = new Object();
        HList n1 = new ListAdapter();
        n1.add(o07);
        assertFalse("Errore : contains", sub.containsAll(n1));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> sub.containsAll(null));

        sub.clear();

        //equals
        sub.add(o01);
        sub.add(o02);
        HList c2 = new ListAdapter();
        c2.addAll(sub);
        assertTrue("Errore : not equals", sub.equals(c2));
    
        sub.clear();

        //get
        sub.add(o02); sub.add(o01);
        assertEquals("Errore : wrong position o02", o02, sub.get(0));
        assertEquals("Errore : wrong position o01", o01, sub.get(1));

        assertThrows("Errore : exception not thrown", IndexOutOfBoundsException.class, () -> sub.get(-1));

        sub.clear();

        //indexof
        sub.add(o01);
        sub.add(o02);

        assertEquals("Errore : wrong position o01", 0, sub.indexOf(o01));
        assertEquals("Errore : wrong position o02", 1, sub.indexOf(o02));
        assertEquals("Errore : wrong position o03", -1, sub.indexOf(o03));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> sub.indexOf(null));

        sub.clear();

        //isempty
        assertTrue("Errore : isn't empty", sub.isEmpty());

        sub.clear();

        //listiterator
        HIterator iter1 = sub.iterator();
        Object[] oa2 = new Object[5];

        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o02);
        sub.add(o01);
        for (int i = 0; iter1.hasNext(); i++) {
            oa2[i] = iter1.next();
        }

        assertArrayEquals("Errore : iteration didn't work", new Object[] { o01, o02, o03, o02, o01 }, oa2);
        iter1.remove();
        assertFalse("Errore : this doesn't have next", iter1.hasNext());
        assertEquals("Errore : the size was not 4", 4, sub.size());

        assertThrows("Errore : exception not thrown", IllegalStateException.class, () -> iter1.remove());

        sub.clear();

        //lastindexof
        sub.add(o01);
        sub.add(o02);
        sub.add(o01);
        sub.add(o02);

        assertEquals("Errore : wrong position o01", 2, sub.lastIndexOf(o01));
        assertEquals("Errore : wrong position o01", 3, sub.lastIndexOf(o02));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> sub.lastIndexOf(null));
        
        sub.clear();

        //listiterator
        HListIterator iter2 = sub.listIterator();

        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o02);
        sub.add(o02);
        iter2.next();
        iter2.next();
        iter2.add(o01);
        assertEquals("Errore : didn't remove", 6, sub.size());
        assertEquals("Errore : insertion went wrong", o01, sub.get(2));
        assertTrue("Errore : apparently has no previous", iter2.hasPrevious());
        assertEquals("Errore : iterator misplaced", o01, iter2.previous());
        iter2.remove();
        assertEquals("Errore : didn't remove", 5, sub.size());
        assertEquals("Errore : iterator prev index check", 1, iter2.previousIndex());
        assertEquals("Errore : iterator next index check", 2, iter2.nextIndex());
        assertThrows("Errore : exception not thrown", IllegalStateException.class, () -> iter2.remove());
        assertEquals("Errore : structure got messed up", o03, iter2.next());

        sub.clear();

        //remove
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);

        assertEquals("Errore : wrong position o01", o01, sub.remove(0));
        assertEquals("Errore : wrong position o02", o02, sub.remove(3));
        assertEquals("Errore : wrong position o03", o03, sub.remove(6));

        assertTrue("Errore : no such element, o01", sub.remove(o01));
        assertTrue("Errore : no such element, o02", sub.remove(o02));
        assertTrue("Errore : no such element, o03", sub.remove(o03));

        assertEquals("Errore : size check", 3, sub.size());
        Object o08 = new Object();
        assertFalse("Errore : o04 was in the list", sub.remove(o08));

        assertThrows("Errore : exception 1 not thrown", IndexOutOfBoundsException.class, () -> sub.remove(-1));
        assertThrows("Errore : exception 2 not thrown", NullPointerException.class, () -> sub.remove(null));
        
        sub.clear();

        //removeall
        HList c3 = new ListAdapter();
        c3.add(o01);
        c3.add(o02);
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o02);
        sub.add(o01);

        assertTrue("Errore : list not modified", sub.removeAll(c3));
        assertEquals("Errore : size check", 3, sub.size());

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> sub.removeAll(null));
    
        sub.clear();

        //retainall
        HList c4 = new ListAdapter();
        c4.add(o01);
        c4.add(o02);
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o02);
        sub.add(o01);

        assertTrue("Errore : list not modified", sub.retainAll(c4));
        assertEquals("Errore : size check", 4, sub.size());

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> sub.retainAll(null));
        
        sub.clear();

        //set
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);
        sub.add(o03);

        assertEquals("Errore : replaced wrong one", o03, sub.set(2, o02));
        assertEquals("Errore : o02 not replaced correctly", o02, sub.get(2));

        assertThrows("Errore : exception 1 not thrown", NullPointerException.class, () -> sub.set(1, null));
        assertThrows("Errore : exception 2 not thrown", IndexOutOfBoundsException.class, () -> sub.set(-1, o01));
    
        sub.clear();

        //size
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);

        assertEquals("Failed: wrong size calculation", 3, sub.size());
    
        sub.clear();

        //toarray
        sub.add(o01);
        sub.add(o02);
        sub.add(o03);

        assertArrayEquals("Errore : wrong copy 1", new Object[] { o01, o02, o03 }, sub.toArray());
        assertArrayEquals("Errore : wrong copy 2", new Object[] { o01, o02, o03, null }, sub.toArray(new Object[4]));
        assertArrayEquals("Errore : didn't resize", new Object[] { o01, o02, o03 }, sub.toArray(new Object[1]));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> sub.toArray(null));

        


    }

    @Test
    public void list_toArray() {
        l.add(o01);
        l.add(o02);
        l.add(o03);

        assertArrayEquals("Errore : wrong copy 1", new Object[] { o01, o02, o03 }, l.toArray());
        assertArrayEquals("Errore : wrong copy 2", new Object[] { o01, o02, o03, null }, l.toArray(new Object[4]));
        assertArrayEquals("Errore : didn't resize", new Object[] { o01, o02, o03 }, l.toArray(new Object[1]));

        assertThrows("Errore : exception not thrown", NullPointerException.class, () -> l.toArray(null));
    }
}
