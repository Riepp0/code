package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {

    private class MySet implements HSet {

        @Override
        public boolean add(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean addAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void clear() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean contains(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean containsAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public HIterator iterator() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean remove(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean removeAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean retainAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int size() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Object[] toArray() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object[] toArray(Object[] a) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private class MyCollection implements HCollection {

        @Override
        public boolean add(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean addAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void clear() {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean contains(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean containsAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean isEmpty() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public HIterator iterator() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean remove(Object o) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean removeAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean retainAll(HCollection c) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public int size() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public Object[] toArray() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Object[] toArray(Object[] a) {
            // TODO Auto-generated method stub
            return null;
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
            return (getKey()==null ? 0 : getKey().hashCode()) ^ (getValue()==null ? 0 : getValue().hashCode());
        }

        @Override
        public Object setValue(Object value) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    Hashtable<Object, Object> hash;

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
        Enumeration<Object> enu = hash.keys();
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
        Enumeration<Object> enu = hash.keys();
        while (enu.hasMoreElements())
            ret.add(enu.nextElement());
        return ret;
    }

}
