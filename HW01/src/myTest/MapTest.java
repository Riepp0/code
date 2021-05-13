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
    private Object v01;
    private Object v02;
    private Object v03;
    private Object v04;
    private Object v05;

    @Before
    public void before() {
        m = new MapAdapter();
        k01 = new Object();
        k02 = new Object();
        k03 = new Object();
        k04 = new Object();
        k05 = new Object();
        v01 = new Object();
        v02 = new Object();
        v03 = new Object();
        v04 = new Object();
        v05 = new Object();
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

        HSet hs = m.entrySet();
        HIterator iter = hs.iterator();
        HMap.HEntry he = (HMap.HEntry) iter.next();
        assertTrue("Errore : ",m.containsKey(he.getKey()));
        assertTrue("Errore : ",m.containsValue(he.getValue()));

        Object old = he.getValue();
        he.setValue(v04);
        assertEquals(v04, m.get(he.getKey()));

        he = (HMap.HEntry) iter.next();
        Object oldk = he.getKey();
        iter.remove();
        assertFalse("Errore : ", m.containsKey(oldk));

        assertThrows("Errore : ", IllegalStateException.class, () -> iter.remove());
        assertEquals("Errore : ", 4, m.size());

        HList hl = new ListAdapter();
        he = (HMap.HEntry) iter.next();
        hl.add(he);
        he = (HMap.HEntry) iter.next();
        hl.add(he);
        assertTrue(hs.containsAll(hl));

        HSet tmp = m.entrySet();
        assertTrue(hs.equals(tmp));

    }

    @Test
    public void map_equals() {
        m.put(k01, v01);
        m.put(k02, v02);
        HMap map = new MapAdapter();
        map.putAll(m);
        assertTrue("Errore : non sono uguali", m.equals(map));
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

    }

}
