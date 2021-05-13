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

            protected Object last;
            protected Enumeration k;
            protected boolean next;

            public Iterator() {
                last = null;
                k = MapAdapter.this.hash.keys();
            }

            @Override
            public boolean hasNext() {
                return k.hasMoreElements();
            }

            @Override
            public Object next() {
                last = k.nextElement();
                next = true;
                return new MyEntry(last, MapAdapter.this.get(last));
            }

            @Override
            public void remove() {
                if (!next)
                    throw new IllegalStateException();
                MapAdapter.this.remove(last);
                next = false;
            }

        }

        protected MapAdapter map;

        public EntrySet() {
            map = MapAdapter.this;
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
            if (o == null)
                throw new NullPointerException();
            if (!(o instanceof HEntry))
                return false;
            HEntry he = (HEntry) o;
            return map.containsKey(he.getKey()) && map.get(he.getKey()).equals(he.getValue());
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
            if (!(o instanceof HSet))
                return false;
            HSet hs = (HSet) o;
            if (size() != hs.size())
                return false;
            if (!containsAll(hs))
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
            if (o == null)
                throw new NullPointerException();
            if (!(o instanceof HEntry))
                return false;
            HEntry he = (HEntry) o;
            if (contains(he)) {
                map.remove(he.getKey());
                return true;
            }
            return false;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            int old = size();
            while (iter.hasNext())
                remove(iter.next());
            return old != size();
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            int old = size();
            while (iter.hasNext()) {
                Object he = iter.next();
                if (!c.contains(he))
                    remove(he);
            }
            return old != size();
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
            if (a == null)
                throw new NullPointerException();
            if (a.length < map.size())
                a = new Object[map.size()];
            HIterator iter = iterator();
            for (int i = 0; i < a.length; i++) {
                a[i] = iter.next();
            }
            return a;
        }
    }

    private class KeySet implements HSet {
        private class Iterator implements HIterator {

            protected Object last;
            protected Enumeration k;
            protected boolean next;

            public Iterator() {
                last = null;
                k = MapAdapter.this.hash.keys();
                next = false;
            }

            @Override
            public boolean hasNext() {
                return k.hasMoreElements();
            }

            @Override
            public Object next() {
                last = k.nextElement();
                next = true;
                return last;
            }

            @Override
            public void remove() {
                if (!next)
                    throw new IllegalStateException();
                MapAdapter.this.remove(last);
                next = false;
            }

        }

        protected MapAdapter map;

        public KeySet() {
            map = MapAdapter.this;
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
            if (!(o instanceof HSet))
                return false;
            HSet hs = (HSet) o;
            if (size() != hs.size())
                return false;
            return containsAll(hs);
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
            int old = size();
            while (iter.hasNext())
                remove(iter.next());
            return old != size();
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            int old = size();
            while (iter.hasNext()) {
                Object he = iter.next();
                if (!c.contains(he))
                    remove(he);
            }
            return old != size();
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
            if (a == null)
                throw new NullPointerException();
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

            protected Object last;
            protected Enumeration k;
            protected boolean next;

            public Iterator() {
                last = null;
                k = MapAdapter.this.hash.elements();
                next = false;
            }

            @Override
            public boolean hasNext() {
                return k.hasMoreElements();
            }

            @Override
            public Object next() {
                last = k.nextElement();
                next = true;
                return MapAdapter.this.get(last);
            }

            @Override
            public void remove() {
                if (!next)
                    throw new IllegalStateException();
                MapAdapter.this.remove(last);
                next = false;
            }

        }

        protected MapAdapter map;

        public ValueCollection() {
            map = MapAdapter.this;
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
            if (!(o instanceof HCollection))
                return false;
            HCollection hs = (HCollection) o;
            if (size() != hs.size())
                return false;
            return containsAll(hs);
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
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            int old = size();
            while (iter.hasNext())
                remove(iter.next());
            return old != size();
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            int old = size();
            while (iter.hasNext()) {
                Object he = iter.next();
                if (!c.contains(he))
                    remove(he);
            }
            return old != size();
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
            if (a == null)
                throw new NullPointerException();
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

        protected Object key, value;
        protected MapAdapter map;

        public MyEntry(Object k, Object v) {
            key = k;
            value = v;
            map = MapAdapter.this;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof HEntry))
                return false;
            HEntry cmp = (HEntry) o;
            return (getKey() == null ? cmp.getKey() == null : getKey().equals(cmp.getKey()))
                    && (getValue() == null ? cmp.getValue() == null : getValue().equals(cmp.getValue()));
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
            MapAdapter.this.put(key, value);
            return ret;
        }
    }

    private Hashtable hash;

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
        return new EntrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof HMap))
            return false;
        HMap cmp = (HMap) o;
        return entrySet().equals(cmp.entrySet());
    }

    @Override
    public Object get(Object key) {
        if (key == null)
            throw new NullPointerException();
        return hash.get(key);
    }

    @Override
    public int hashCode() {
        return entrySet().hashCode();
    }

    @Override
    public boolean isEmpty() {
        return hash.isEmpty();
    }

    @Override
    public HSet keySet() {
        return new KeySet();
    }

    @Override
    public Object put(Object key, Object value) {
        return hash.put(key, value);
    }

    @Override
    public void putAll(HMap t) {
        if (t == null)
            throw new NullPointerException();
        HSet k = t.keySet();
        HIterator iter = k.iterator();
        while (iter.hasNext()) {
            Object tempkey = iter.next();
            put(tempkey, t.get(tempkey));
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
        return new ValueCollection();
    }
}