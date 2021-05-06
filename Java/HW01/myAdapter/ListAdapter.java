/**
 * @author Rieppi Michele 1218669
 */


 /**
  * STRUTTURA:
  * ListAdapter[HList]:{ Iterator[HIterator]
  *                    { ListIterator(Iterator)[HListIterator]
  *                    { Sublist(ListAdapter):{ SubListIterator[HIterator]
  *                                           { SubListListIterator(SubListIterator)[HListIterator]
  *                                           
  */
package myAdapter;

import java.util.NoSuchElementException;
import java.util.Vector;

public class ListAdapter implements HList {
    /**
     * SOTTOCLASSI:
     * 
     * Iterator di ListAdapter, attraversa il vettore di ListAdapter
     * 
     * ListIterator di ListAdapter, attraversa il vettore di ListAdapter da entrambi
     * i lati
     * 
     * SubList di ListAdapter, sottolista in cui sono compresi solo alcuni degli
     * elementi Nella Sublist sono presenti entrambi i tipi di Iterator che sono
     * stati realizzati per ListAdapter
     */
    private class Iterator implements HIterator {

        protected Vector<Object> v;

        /**
         * Indice della posizione dell'iteratore
         */
        protected int index;

        /**
         * Variabile usata per iterare
         */
        protected boolean next;

        /**
         * Costruttore di Iterator partendo dall'indice del vettore
         * 
         * @param index
         */
        public Iterator(int index) {
            if (index < 0 || index > size())
                throw new IllegalArgumentException();
            v = vector;
            this.index = index;
            next = false;
        }

        /**
         * Costruttore di Iterator
         *
         */
        public Iterator() {
            this(0);
        }

        @Override
        public boolean hasNext() {
            return index < v.size() - 1;
        }

        @Override
        public Object next() {
            if (index >= v.size())
                throw new NoSuchElementException();
            next = true;
            return v.elementAt(index++);
        }

        @Override
        public void remove() {
            if (!next)
                throw new IllegalStateException();
            next = false;
            v.removeElement(--index);

        }

    }

    private class ListIterator extends Iterator implements HListIterator {
        /**
         * Variabile per iterare inversamente
         */
        protected boolean prev;

        /**
         * Costruttore di ListIterator
         * 
         */
        public ListIterator() {
            this(0);
            prev = false;
        }

        /**
         * Costruttore di ListIterator dato l'indice da cui partire
         * 
         * @param index
         */
        public ListIterator(int index) {
            super(index);
            prev = false;
        }

        @Override
        public void add(Object o) {
            if (o == null)
                throw new NullPointerException();
            v.insertElementAt(o, index++);
            next = prev = false;

        }

        @Override
        public boolean hasNext() {
            return super.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public Object next() {
            next = true;
            prev = false;
            return super.next();
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public Object previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            next = false;
            prev = true;
            return v.elementAt(--index);
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (!next && !prev)
                throw new IllegalStateException();
            if (prev) {
                v.removeElementAt(index);
                prev = next = false;
            } else
                super.remove();
        }

        @Override
        public void set(Object o) {
            if (o == null)
                throw new NullPointerException();
            if (v.isEmpty())
                add(o);
            if (!next && !prev)
                throw new IllegalStateException();
            if (next)
                v.setElementAt(o, index - 1);
            if (prev)
                v.setElementAt(o, index);
        }
    }

    private class SubList extends ListAdapter {
        private class SubListIterator implements HIterator {

            protected Vector<Object> v;

            /**
             * Indice della posizione dell'iteratore
             */
            protected int index;

            /**
             * Variabile usata per iterare
             */
            protected boolean next;

            /**
             * Costruttore di SubListIterator partendo dall'indice del vettore
             * 
             * @param v
             * @param index
             */
            public SubListIterator(int index) {
                if (index < from || index > to)
                    throw new IllegalArgumentException();
                v = vector;
                this.index = index;
                next = false;
            }

            /**
             * Costruttore di SubListIterator
             */
            public SubListIterator() {
                this(0);
            }

            @Override
            public boolean hasNext() {
                return index < to;
            }

            @Override
            public Object next() {
                if (index >= v.size())
                    throw new NoSuchElementException();
                next = true;
                return v.elementAt(index++);
            }

            @Override
            public void remove() {
                if (!next)
                    throw new IllegalStateException();
                next = false;
                v.removeElement(--index);
                to--;
            }

        }

        private class SubListListIterator extends SubListIterator implements HListIterator {
            /**
             * Variabile per iterare inversamente
             */
            protected boolean prev;

            /**
             * Costruttore di SubListListIterator
             * 
             */
            public SubListListIterator() {
                this(0);
                prev = false;
            }

            /**
             * Costruttore di SubListListIterator dato l'indice da cui partire
             * 
             * @param index
             */
            public SubListListIterator(int index) {
                super(index);
                prev = false;
            }

            @Override
            public void add(Object o) {
                if (o == null)
                    throw new NullPointerException();
                v.insertElementAt(o, index++);
                next = prev = false;
                to++;
            }

            @Override
            public boolean hasNext() {
                return super.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return index > from;
            }

            @Override
            public Object next() {
                next = true;
                prev = false;
                return super.next();
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public Object previous() {
                if (!hasPrevious())
                    throw new NoSuchElementException();
                next = false;
                prev = true;
                return v.elementAt(--index);
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                if (!next && !prev)
                    throw new IllegalStateException();
                if (prev) {
                    v.removeElementAt(index);
                    prev = next = false;
                    to--;
                } else
                    super.remove();
            }

            @Override
            public void set(Object o) {
                if (o == null)
                    throw new NullPointerException();
                if (from == to)
                    add(o);
                if (!next && !prev)
                    throw new IllegalStateException();
                if (next)
                    v.setElementAt(o, index - 1);
                if (prev)
                    v.setElementAt(o, index);
            }
        }

        /**
         * Indica l'indice più basso
         */
        private int from;

        /**
         * Indica l'indice più alto (non compreso)
         */
        private int to;

        /**
         * Costruttore a partire dagli indici
         * 
         * @param f
         * @param t
         */
        public SubList(int f, int t) {
            if (f < 0 || t > size() || f > t)
                throw new IllegalArgumentException();
            from = f;
            to = t;
        }

        @Override
        public void add(int index, Object element) {
            if (element == null)
                throw new NullPointerException();
            if (index < from || index >= to)
                throw new IndexOutOfBoundsException();
            vector.insertElementAt(element, index);
            to++;

        }

        @Override
        public boolean add(Object o) {
            if (o == null)
                throw new NullPointerException();
            vector.insertElementAt(o, to - 1);
            to++;
            return true;
        }

        @Override
        public boolean addAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            if (c.isEmpty())
                return false;
            HIterator iter = c.iterator();
            while (iter.hasNext())
                add(iter.next());
            return true;
        }

        @Override
        public boolean addAll(int index, HCollection c) {
            if (c == null)
                throw new NullPointerException();
            if (index < from || index >= to)
                throw new IndexOutOfBoundsException();
            if (c.isEmpty())
                return false;
            HIterator iter = c.iterator();
            while (iter.hasNext())
                add(index, iter.next());
            return true;
        }

        @Override
        public void clear() {
            for (int i = from; i < to; i++)
                vector.removeElementAt(i);
            to = from;

        }

        @Override
        public boolean contains(Object o) {
            if (o == null)
                throw new NullPointerException();
            for (int i = from; i < to; i++) {
                if (vector.elementAt(i).equals(o))
                    return true;
            }
            return false;
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
            if (!(this.getClass().isInstance(o)))
                return false;
            SubList tmp = (SubList) o;
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
        public Object get(int index) {
            if (index < from || index >= to)
                throw new IndexOutOfBoundsException();
            return vector.elementAt(index);
        }

        @Override
        public int indexOf(Object o) {
            if (o == null)
                throw new NullPointerException();
            for (int i = from; i < to; i++) {
                if (vector.elementAt(i).equals(o))
                    return i - from;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return from == to;
        }

        @Override
        public HIterator iterator() {
            return new SubListIterator();
        }

        @Override
        public int lastIndexOf(Object o) {
            if (o == null)
                throw new NullPointerException();
            for (int i = to - 1; i >= from; i--) {
                if (vector.elementAt(i).equals(o))
                    return i - from;
            }
            return -1;
        }

        @Override
        public HListIterator listIterator() {
            return new SubListListIterator();
        }

        @Override
        public HListIterator listIterator(int index) {
            if (index < from || index >= to)
                throw new IndexOutOfBoundsException();
            return new SubListListIterator(index);
        }

        @Override
        public Object remove(int index) {
            if (index < from || index >= to)
                throw new IndexOutOfBoundsException();
            Object ret = vector.elementAt(index);
            vector.removeElementAt(index);
            to--;
            return ret;
        }

        @Override
        public boolean remove(Object o) {
            if (o == null)
                throw new NullPointerException();
            int index = indexOf(o);
            if (index == -1)
                return false;
            remove(index);
            return true;
        }

        @Override
        public boolean removeAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = c.iterator();
            while (iter.hasNext()) {
                Object remove = iter.next();
                if (contains(remove))
                    remove(remove);
            }
            return true;
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            HIterator iter = iterator();
            while (iter.hasNext())
                if (!c.contains(iter.next()))
                    remove(iter);
            return true;
        }

        @Override
        public Object set(int index, Object element) {
            if (element == null)
                throw new NullPointerException();
            if (index < from || index >= to)
                throw new IndexOutOfBoundsException();
            Object ret = vector.elementAt(index);
            vector.setElementAt(element, index);
            return ret;
        }

        @Override
        public int size() {
            return to - from;
        }

        @Override
        public HList subList(int fromIndex, int toIndex) {
            if (fromIndex < from || toIndex > to)
                throw new IndexOutOfBoundsException();
            return new SubList(fromIndex, toIndex);
        }

        @Override
        public Object[] toArray() {
            Object[] ret = new Object[size()];
            for (int i = from; i < to; i++)
                ret[i - from] = vector.elementAt(i);
            return ret;
        }

        @Override
        public Object[] toArray(Object[] a) {
            if (a == null)
                throw new NullPointerException();
            if (a.length < size())
                a = new Object[size()];
            for (int i = from; i < to; i++)
                a[i - from] = vector.elementAt(i);
            return a;

        }

    }

    /**
     * Vettore con cui realizzo la lista
     */
    private Vector<Object> vector;

    public void add(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        if (index < 0 || index > vector.size())
            throw new IndexOutOfBoundsException();
        vector.insertElementAt(element, index);
    }

    @Override
    public boolean add(Object o) {
        if (o == null)
            throw new NullPointerException();
        vector.addElement(o);
        return true;
    }

    @Override
    public boolean addAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (c.isEmpty())
            return false;
        HIterator iter = c.iterator();
        while (iter.hasNext())
            add(iter.next());
        return true;
    }

    @Override
    public boolean addAll(int index, HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        if (c.isEmpty())
            return false;
        HIterator iter = c.iterator();
        while (iter.hasNext())
            add(index, iter.next());
        return true;
    }

    @Override
    public void clear() {
        vector.removeAllElements();

    }

    @Override
    public boolean contains(Object o) {
        if (o == null)
            throw new NullPointerException();
        return vector.contains(o);
    }

    @Override
    public boolean containsAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            if (!vector.contains(iter.next()))
                return false;
        return true;
    }

    @Override
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
    public Object get(int index) {
        if (index < 0 || index >= vector.size())
            throw new IndexOutOfBoundsException();
        return vector.elementAt(index);
    }

    public int hashCode() {
        return vector.hashCode();
    }

    @Override
    public int indexOf(Object o) {
        if (o == null)
            throw new NullPointerException();
        return vector.indexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return vector.isEmpty();
    }

    @Override
    public HIterator iterator() {
        return new Iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null)
            throw new NullPointerException();
        return vector.lastIndexOf(o);
    }

    @Override
    public HListIterator listIterator() {
        return new ListIterator();
    }

    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > vector.size())
            throw new IndexOutOfBoundsException();
        return new ListIterator(index);
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        Object ret = vector.elementAt(index);
        vector.elementAt(index);
        return ret;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
            throw new NullPointerException();
        return vector.removeElement(o);
    }

    @Override
    public boolean removeAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        HIterator iter = c.iterator();
        while (iter.hasNext()) {
            Object remove = iter.next();
            if (contains(remove))
                remove(remove);
        }
        return true;
    }

    @Override
    public boolean retainAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        HIterator iter = iterator();
        while (iter.hasNext())
            if (!c.contains(iter.next()))
                remove(iter);
        return true;
    }

    @Override
    public Object set(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        Object ret = vector.elementAt(index);
        vector.setElementAt(element, index);
        return ret;
    }

    @Override
    public int size() {
        return vector.size();
    }

    @Override
    public HList subList(int fromIndex, int toIndex) {
        return new SubList(fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        Object[] ret = new Object[size()];
        for (int i = 0; i < size(); i++)
            ret[i] = vector.elementAt(i);
        return ret;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a == null)
            throw new NullPointerException();
        if (a.length < size())
            a = new Object[size()];
        for (int i = 0; i < size(); i++)
            a[i] = vector.elementAt(i);
        return a;

    }

}