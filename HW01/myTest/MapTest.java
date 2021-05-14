package myTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import myAdapter.*;

public class MapTest {
    private MapAdapter m;
    private Object k01;
    private Object k02;
    private Object k03;
    private Object k04;
    private Object k05;
    private Object k06;
    private Object v01;
    private Object v02;
    private Object v03;
    private Object v04;
    private Object v05;
    private Object v06;

    @Before
    public void before() {
        m = new MapAdapter();
        k01 = new Object();
        k02 = new Object();
        k03 = new Object();
        k04 = new Object();
        k05 = new Object();
        k06 = new Object();
        v01 = new Object();
        v02 = new Object();
        v03 = new Object();
        v04 = new Object();
        v05 = new Object();
        v06 = new Object();
    }

    @Test
    public void map_clear() {
        assertTrue("Errore : la mappa non è vuota", m.isEmpty());
        m.put(k01, v01);
        assertFalse("Errore : la mappa non è vuota 2", m.isEmpty());
        m.clear();
        assertTrue("Errore : la mappa non è vuota", m.isEmpty());
    }

    @Test
    public void map_containsKey() {
        m.put(k01, v01);
        assertTrue("Errore : la mappa non contiene la chiave", m.containsKey(k01));
        assertThrows("Errore : non viene lanciata l'eccezione", NullPointerException.class, () -> m.containsKey(null));
    }

    @Test
    public void map_containsValue() {
        m.put(k01, v01);
        assertTrue("Errore : la mappa non contiene il valore", m.containsValue(v01));
    }

    @Test
    public void map_entrySet() {
        m.put(k01, v01);
        m.put(k02, v02);
        m.put(k03, v03);
        m.put(k04, v04);
        m.put(k05, v05);

        HSet es = m.entrySet();
        HIterator iter = es.iterator();
        HMap.HEntry e = (HMap.HEntry) iter.next();
        assertTrue("Errore : non contiene k", m.containsKey(e.getKey()));
        assertTrue("Errore : non contiene v", m.containsValue(e.getValue()));

        Object oldval = e.getValue();
        e.setValue(v06);
        assertFalse("Errore : non c'è il backing setValue", m.containsValue(oldval));
        assertEquals("Errore : non è stato rimpiazzato", v06, m.get(e.getKey()));

        e = (HMap.HEntry) iter.next();
        Object oldkey = e.getKey();
        iter.remove();
        assertFalse(m.containsKey(oldkey));
        assertEquals("Errore : non è stato rimosso", 4, es.size());
        assertEquals("Errore : backing su", 4, m.size());
        assertThrows("Errore : impossibile chiamare un remove dopo un remove", IllegalStateException.class, () -> iter.remove());

        HList l = new ListAdapter();
        e = (HMap.HEntry) iter.next();
        l.add(e);
        e = (HMap.HEntry) iter.next();
        l.add(e);
        assertTrue("Errore : non contiene elementi dal proprio iteratore", es.containsAll(l));

        assertTrue("Errore : non è stato rimosso l'elemento contenuto", es.remove(e));
        assertFalse("Errore : contiene ancora l'elemento rimosso", es.contains(e));
        m.put(e.getKey(), e.getValue());
        assertTrue("Errore : non è stato rimosso l'elemento contenuto", es.removeAll(l));
        assertThrows("Errore : removing null", NullPointerException.class, () -> es.removeAll(null));
        assertTrue("Errore : non sono stati rimosso gli elementi contenuto nella collection", es.retainAll(l));
        assertThrows("Errore : retaining null", NullPointerException.class, () -> es.retainAll(null));

        m.clear();
        m.put(k01, v01);
        m.put(k02, v02);
        m.put(k03, v03);
        m.put(k04, v04);
        m.put(k05, v05);
        HSet es2 = m.entrySet();

        l.clear();
        HIterator it = es2.iterator();
        while (it.hasNext()) {
            l.add(it.next());
        }
        Object[] arr = es2.toArray();
        assertEquals(l.size(), arr.length);
        for (int i = 0; i < arr.length; i++) {
            assertTrue(l.contains(arr[i]));
        }
    }
    @Test
    public void map_equals() {
        m.put(k01, v01);
        m.put(k02, v02);
        HMap m = new MapAdapter();
        m.putAll(m);
        assertTrue("Errore : non sono uguali", m.equals(m));
    }

    @Test
    public void map_get() {
        m.put(k01, v01);
        assertEquals("Errore : non ha il valore corretto", v01, m.get(k01));
        assertThrows("Errore : eccezione non lanciata", NullPointerException.class, () -> m.get(null));
    }

    @Test
    public void map_isEmpty() {
        assertTrue("Errore : non ha dimensione 0", m.isEmpty());
    }

    @Test
    public void map_keySet() {
        m.put(k01, v01);
        m.put(k02, v02);
        m.put(k03, v03);
        m.put(k04, v04);
        m.put(k05, v05);

        HSet ks = m.keySet();
        HIterator iter = ks.iterator();
        Object e = iter.next();
        assertTrue("Errore : non contiene v", m.containsKey(e));

        e = iter.next();
        Object oldkey = e;
        iter.remove();
        assertFalse(m.containsKey(oldkey));
        assertEquals("Errore : non è stato rimosso", 4, ks.size());
        assertEquals("Errore : backing su", 4, m.size());
        assertThrows("Errore : impossibile chiamare un remove dopo un remove", IllegalStateException.class, () -> iter.remove());

        HList l = new ListAdapter();
        e = iter.next();
        l.add(e);
        e = iter.next();
        l.add(e);
        assertTrue("Errore : non contiene elementi del proprio iteratore", ks.containsAll(l));

        assertTrue("Errore : non è stato rimosso l'elemento contenuto", ks.remove(e));
        assertFalse("Errore : contiene ancora l'elemento rimosso", ks.contains(e));
        m.put(e, v06);
        assertTrue("Errore : non è stato rimosso l'elemento contenuto", ks.removeAll(l));
        assertThrows("Errore : removing null", NullPointerException.class, () -> ks.removeAll(null));
        assertTrue("Errore : non sono stati rimosso gli elementi contenuto nella collection", ks.retainAll(l));
        assertThrows("Errore : retaining null", NullPointerException.class, () -> ks.retainAll(null));

        m.clear();
        m.put(k01, v01);
        m.put(k02, v02);
        m.put(k03, v03);
        m.put(k04, v04);
        m.put(k05, v05);
        HSet ks2 = m.keySet();

        l.clear();
        HIterator it = ks2.iterator();
        while (it.hasNext()) {
            l.add(it.next());
        }
        Object[] arr = ks2.toArray();
        assertEquals(l.size(), arr.length);
        for (int i = 0; i < arr.length; i++) {
            assertTrue(l.contains(arr[i]));
        }

    }

    @Test
    public void map_put() {
        m.put(k01, v01);
        assertEquals("Errore : non ha dimensione 1", 1, m.size());
        m.put(k01, v02);
        assertEquals("Errore : non ha sovrascritto", v02, m.get(k01));
        m.put(k02, v02);
        assertEquals("Errore : non ha inserito due chiavi con stesso valore", v02, m.get(k02));
    }

    @Test
    public void map_putAll() {
        HMap c = new MapAdapter();
        c.put(k01, v01);
        c.put(k02, v02);
        c.put(k03, v03);
        c.put(k04, v04);
        m.putAll(c);
        assertEquals("Errore : non è stata inserita la prima coppia", v01, m.get(k01));
        assertEquals("Errore : non è stata inserita la seconda coppia", v02, m.get(k02));
        assertEquals("Errore : non è stata inserita la terza coppia", v03, m.get(k03));
        assertEquals("Errore : non è stata inserita la quarta coppia", v04, m.get(k04));
        assertThrows("Errore : non è stata lanciata l'eccezione", NullPointerException.class, () -> m.putAll(null));
    }

    @Test
    public void map_remove() {
        m.put(k01, v01);
        assertEquals("Errore : rimosso un valore sbagliato", v01, m.remove(k01));
        assertNull("Errore : la mappa non doveva contenere k01", m.remove(k01));
        assertThrows("Errore : non è stata lanciata l'eccezione", NullPointerException.class, () -> m.remove(null));
    }

    @Test
    public void map_size() {
        m.put(k01, v01);
        m.put(k02, v02);
        assertEquals("Errore : la dimensione della mappa non è 2", 2, m.size());
    }

    @Test
    public void map_values() {
        m.put(k01, v01);
        m.put(k02, v02);
        m.put(k03, v03);
        m.put(k04, v04);
        m.put(k05, v05);

        HCollection vc = m.values();
        HIterator iter = vc.iterator();
        Object e = iter.next();
        assertTrue("Errore : non contiene k", m.containsValue(e));

        e = iter.next();
        Object oldval = e;
        iter.remove();
        assertFalse(m.containsValue(oldval));
        assertEquals("Errore : non è stato rimosso", 4, vc.size());
        assertEquals("Errore : backing su", 4, m.size());
        assertThrows("Errore : impossibile chiamare un remove dopo un remove", IllegalStateException.class, () -> iter.remove());

        HList l = new ListAdapter();
        e = iter.next();
        l.add(e);
        e = iter.next();
        l.add(e);
        assertTrue("Errore : non contiene elementi del proprio iteratore", vc.containsAll(l));

        assertTrue("Errore : non è stato rimosso l'elemento contenuto", vc.remove(e));
        assertFalse("Errore : contiene ancora l'elemento rimosso", vc.contains(e));
        m.put(k06, e);
        assertTrue("Errore : non è stato rimosso l'elemento contenuto", vc.removeAll(l));
        assertThrows("Errore : removing null", NullPointerException.class, () -> vc.removeAll(null));
        assertTrue("Errore : non sono stati rimosso gli elementi contenuto nella collection", vc.retainAll(l));
        assertThrows("Errore : retaining null", NullPointerException.class, () -> vc.retainAll(null));

        m.clear();
        m.put(k01, v01);
        m.put(k02, v02);
        m.put(k03, v03);
        m.put(k04, v04);
        m.put(k05, v05);
        HCollection vc2 = m.values();

        l.clear();
        HIterator it = vc2.iterator();
        while (it.hasNext()) {
            l.add(it.next());
        }
        Object[] arr = vc2.toArray();
        assertEquals(l.size(), arr.length);
        for (int i = 0; i < arr.length; i++) {
            assertTrue(l.contains(arr[i]));
        }
    }
}
