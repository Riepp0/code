/**
 * @author Rieppi Michele 1218669
 */

/**
 * STRUTTURA:
 * MapAdapter[HMap]:{ EntrySet[HSet]              :{ Iterator[HIterator]
 *                  { KeySet[HSet]                :{ Iterator[HIterator]
 *                  { ValueCollection[HCollection]:{ Iterator[HIterator]
 *                  { MyEntry[HEntry]
 *                                           
 */
package myAdapter;

import java.util.Enumeration;
import java.util.Hashtable;

public class MapAdapter implements HMap {
    private class EntrySet implements HSet {
        private class Iterator implements HIterator {

            protected HIterator ksi;

            public Iterator() {
                ksi = map.keySet().iterator();
            }

            @Override
            public boolean hasNext() {
                return ksi.hasNext();
            }

            @Override
            public Object next() {
                Object k = ksi.next();
                return new MyEntry(k, map.get(k), map);
            }

            @Override
            public void remove() {
                ksi.remove();
            }

        }

        protected MapAdapter map;

        public EntrySet(MapAdapter m) {
            map = m;
        }

        @Override
        public boolean add(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(HCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            map.clear();
        }

        @Override
        public boolean contains(Object o) {
            if (!MyEntry.class.isInstance(o)) 
                return false;
            MyEntry entry = (MyEntry) o;
            if (!map.containsKey(entry.getKey())) 
                return false;
            if (!map.get(entry.getKey()).equals(entry.getValue())) 
                return false;
            return true;
        }

        @Override
        public boolean containsAll(HCollection c) {
            if (c == null) 
                throw new NullPointerException();
            HIterator iter = c.iterator();
            while (iter.hasNext()) {
                if (!contains(iter.next()))
                    return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (!HSet.class.isInstance(o))
                return false;
            HSet set = (HSet) o;
            if (size() != set.size())
                return false;
            if (!containsAll(set))
                return false;
            return true;
        }

        @Override
        public int hashCode() {
            HIterator iter = iterator();
            int h = 0;
            while (iter.hasNext())
                h += iter.next().hashCode();
            return h;
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public HIterator iterator() {
            return new Iterator();
        }

        @Override
        public boolean remove(Object o) {
            if (!MyEntry.class.isInstance(o))
                return false;
            MyEntry entry = (MyEntry) o;
            if (contains(entry.getKey())) {
                map.remove(entry.getKey());
                return true;
            }
            return false;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            boolean ret = false;
            while (iter.hasNext()) {
                if (remove(iter.next()))
                    ret = true;
            }
            return ret;
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            boolean ret = false;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (!c.contains(o)) {
                    remove(o);
                    ret = true;
                }
            }
            return ret;
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public Object[] toArray() {
            Object[] a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                MyEntry entry = (MyEntry) iter.next();
                a[i] = new MyEntry(entry.getKey(), entry.getValue(), map);
            }
            return a;
        }

        @Override
        public Object[] toArray(Object[] a) {
            if (a.length < map.size())
                a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                MyEntry entry = (MyEntry) iter.next();
                a[i] = new MyEntry(entry.getKey(), entry.getValue(), map);
            }
            return a;
        }
    }

    private class KeySet implements HSet {
        private class Iterator implements HIterator {
            protected Object lastkey;
            protected Enumeration k;

            public Iterator() {
                lastkey = null;
                k = map.hash.keys();
            }

            @Override
            public boolean hasNext() {
                return k.hasMoreElements();
            }

            @Override
            public Object next() {
                lastkey = k.nextElement();
                return lastkey;
            }

            @Override
            public void remove() {
                map.remove(lastkey);
                k = map.hash.keys();
            }

        }

        protected MapAdapter map;

        public KeySet(MapAdapter m) {
            map = m;
        }

        @Override
        public boolean add(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(HCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            map.clear();
        }

        @Override
        public boolean contains(Object o) {
            return map.containsKey(o);
        }

        @Override
        public boolean containsAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            while (iter.hasNext()) {
                if (!contains(iter.next()))
                    return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (!HSet.class.isInstance(o))
                return false;
            HSet set = (HSet) o;
            if (size() != set.size())
                return false;
            if (!containsAll(set))
                return false;
            return true;
        }

        @Override
        public int hashCode() {
            HIterator iter = iterator();
            int h = 0;
            while (iter.hasNext()) {
                h += iter.next().hashCode();
            }
            return h;
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public HIterator iterator() {
            return new Iterator();
        }

        @Override
        public boolean remove(Object o) {
            if (contains(o)) {
                map.remove(o);
                return true;
            }
            return false;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            boolean ret = false;
            while (iter.hasNext()) {
                if (remove(iter.next()))
                    ret = true;
            }
            return ret;
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            boolean ret = false;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (!c.contains(o)) {
                    remove(o);
                    ret = true;
                }
            }
            return ret;
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public Object[] toArray() {
            Object[] a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                a[i] = iter.next();
            }
            return a;
        }

        @Override
        public Object[] toArray(Object[] a) {
            if (a.length < map.size())
                a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                a[i] = iter.next();
            }
            return a;
        }
    }

    private class ValueCollection implements HCollection {
        private class Iterator implements HIterator {

            protected Object lastvalue;
            protected Enumeration v;

            public Iterator() {
                lastvalue = null;
                v = map.hash.elements();
            }

            @Override
            public boolean hasNext() {
                return v.hasMoreElements();
            }

            @Override
            public Object next() {
                lastvalue = v.nextElement();
                return lastvalue;
            }

            @Override
            public void remove() {
                HSet ks = map.keySet();
                HIterator iter = ks.iterator();
                while (iter.hasNext()) {
                    Object k = iter.next();
                    if (map.get(k).equals(lastvalue)) {
                        map.remove(k);
                        return;
                    }
                }
            }

        }

        protected MapAdapter map;

        public ValueCollection(MapAdapter m) {
            map = m;
        }

        @Override
        public boolean add(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(HCollection c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            map.clear();
        }

        @Override
        public boolean contains(Object o) {
            return map.containsValue(o);
        }

        @Override
        public boolean containsAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            while (iter.hasNext()) {
                if (!contains(iter.next()))
                    return false;
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (!HSet.class.isInstance(o))
                return false;
            HSet set = (HSet) o;
            if (size() != set.size())
                return false;
            if (!containsAll(set))
                return false;
            return true;
        }

        @Override
        public int hashCode() {
            HIterator iter = iterator();
            int h = 0;
            while (iter.hasNext()) {
                h += iter.next().hashCode();
            }
            return h;
        }

        @Override
        public boolean isEmpty() {
            return map.isEmpty();
        }

        @Override
        public HIterator iterator() {
            return new Iterator();
        }

        @Override
        public boolean remove(Object o) {
            if (contains(o)) {
                HSet ks = map.keySet();
                HIterator iter = ks.iterator();
                while (iter.hasNext()) {
                    Object k = iter.next();
                    if (map.get(k).equals(o)) {
                        map.remove(k);
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c == null) {
                throw new NullPointerException();
            }
            HIterator iter = c.iterator();
            boolean ret = false;
            while (iter.hasNext()) {
                if (remove(iter.next()))
                    ret = true;
            }
            return ret;
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            boolean ret = false;
            while (iter.hasNext()) {
                Object o = iter.next();
                if (!c.contains(o)) {
                    remove(o);
                    ret = true;
                }
            }
            return ret;
        }

        @Override
        public int size() {
            return map.size();
        }

        @Override
        public Object[] toArray() {
            Object[] a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                a[i] = iter.next();
            }
            return a;
        }

        @Override
        public Object[] toArray(Object[] a) {
            if (a.length < map.size())
                a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                a[i] = iter.next();
            }
            return a;
        }
    }

    private class MyEntry implements HEntry {
        private MapAdapter map;
        private Object key, value;

        public MyEntry(Object k, Object v, MapAdapter m) {
            key = k;
            value = v;
            map = m;
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
            map.put(key, value);
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
        return new EntrySet(this);
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
        return new KeySet(this);
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
        return new ValueCollection(this);
    }
}
