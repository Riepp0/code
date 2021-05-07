/**
 * @author Rieppi Michele 1218669
 */

/**
 * STRUTTURA:
 * MapAdapter[HMap]:{ MyCollection[HCollection]
 *                  { MySet(MyCollection)[HSet]
 *                  { MyEntry(HEntry)
 *                                           
 */
package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {
    /**
     * SOTTOCLASSI:
     * 
     * 
     * 
     */
    private class MyCollection implements HCollection {

        public ListAdapter list;

        public MyCollection() {
            list = new ListAdapter();
        }

        @Override
        public boolean add(Object o) {
            return list.add(o);
        }

        @Override
        public boolean addAll(HCollection c) {
            return list.addAll(c);
        }

        @Override
        public void clear() {
            list.clear();
        }

        @Override
        public boolean contains(Object o) {
            return list.contains(o);
        }

        @Override
        public boolean containsAll(HCollection c) {
            return list.containsAll(c);
        }

        public boolean equals(Object o) {
            if (!(this.getClass().isInstance(o)))
                return false;
            ListAdapter tmp = (ListAdapter) o;
            if (tmp.size() != size())
                return false;
            HIterator iter = tmp.iterator();
            HIterator itera = iterator();
            while (itera.hasNext())
                if (!itera.next().equals(iter.next()))
                    return false;
            return true;
        }

        @Override
        public int hashCode() {
            return list.hashCode();
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }

        @Override
        public HIterator iterator() {
            return list.iterator();
        }

        @Override
        public boolean remove(Object o) {
            return list.remove(o);
        }

        @Override
        public boolean removeAll(HCollection c) {
            return list.removeAll(c);
        }

        @Override
        public boolean retainAll(HCollection c) {
            return list.retainAll(c);
        }

        @Override
        public int size() {
            return list.size();
        }

        @Override
        public Object[] toArray() {
            return list.toArray();
        }

        @Override
        public Object[] toArray(Object[] a) {
            return list.toArray(a);
        }

    }

    private class MySet extends MyCollection implements HSet {

        public MySet() {
            super();
        }

        @Override
        public boolean add(Object o) {
            if (list.contains(o))
                return false;
            return list.add(o);
        }

        @Override
        public boolean addAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            while (iter.hasNext())
                add(iter.next());
            return true;
        }
    }

    private class MyEntry implements HEntry {

        private Object key, value;

        public MyEntry(Object k, Object v) {
            key = k;
            value = v;
        }

        @Override
        public boolean equals(Object o) {
            if (!(this.getClass().isInstance(o)))
                return false;
            MyEntry tmp = (MyEntry) o;
            return (getKey() == null ? tmp.getKey() == null : getKey().equals(tmp.getKey()))
                    && (getValue() == null ? tmp.getValue() == null : getValue().equals(tmp.getValue()));
        }

        @Override
        public Object getKey() {
            return key;
        }

        @Override
        public Object getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
        }

        @Override
        public Object setValue(Object value) {
            if (value == null)
                throw new NullPointerException();
            Object ret = this.value;
            this.value = value;
            return ret;
        }

    }

    Hashtable hash;

    public MapAdapter() {
        hash = new Hashtable();
    }

    @Override
    public void clear() {
        hash.clear();

    }

    @Override
    public boolean containsKey(Object key) {
        if (key == null)
            throw new NullPointerException();
        return hash.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return hash.contains(value);
    }

    @Override
    public HSet entrySet() {
        HSet ret = new MySet();
        HSet k = keySet();
        HIterator iter = k.iterator();
        while (iter.hasNext()) {
            Object tmpkey = iter.next();
            ret.add(new MyEntry(tmpkey, get(tmpkey)));
        }
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (!(this.getClass().isInstance(o)))
            return false;
        MapAdapter tmp = (MapAdapter) o;
        return entrySet().equals(tmp.entrySet());
    }

    @Override
    public Object get(Object key) {
        if (key == null)
            throw new NullPointerException();
        return hash.get(key);
    }

    @Override
    public int hashCode() {
        HSet e = entrySet();
        HIterator iter = e.iterator();
        int ret = 0;
        while (iter.hasNext())
            ret += iter.next().hashCode();
        return ret;
    }

    @Override
    public boolean isEmpty() {
        return hash.isEmpty();
    }

    @Override
    public HSet keySet() {
        HSet ret = new MySet();
        Enumeration enu = hash.keys();
        while (enu.hasMoreElements())
            ret.add(enu.nextElement());
        return ret;
    }

    @Override
    public Object put(Object key, Object value) {
        return hash.put(key, value);
    }

    @Override
    public void putAll(HMap t) {
        if (t == null) {
            throw new NullPointerException();
        }
        HSet k = t.keySet();
        HIterator iter = k.iterator();
        while (iter.hasNext()) {
            Object tmpkey = iter.next();
            put(tmpkey, t.get(tmpkey));
        }

    }

    @Override
    public Object remove(Object key) {
        if (key == null)
            throw new NullPointerException();
        return hash.remove(key);
    }

    @Override
    public int size() {
        return hash.size();
    }

    @Override
    public HCollection values() {
        HCollection ret = new MyCollection();
        Enumeration enu = hash.keys();
        while (enu.hasMoreElements())
            ret.add(enu.nextElement());
        return ret;
    }

}
