package myTest;

import org.junit.Test;
import myAdapter.*;

public class MapTest {
    @Test
    public void mt01_adding() {
        MapAdapter m = new MapAdapter();
        Object k1 = new Object(), k2 = new Object(), k3 = new Object(), v1 = new Object(), v2 = new Object(), v3 = new Object();
        m.put(k1, v1);
        HSet s = m.entrySet();
        System.out.println(m.size());
        s.clear();
        System.out.println(m.size());
        
    }
}
