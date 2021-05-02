package myAdapter;

import java.util.NoSuchElementException;
import java.util.Vector;

public class ListAdapter implements HList {
    // Subclasses

    /**
     * Iteratore di ListAdapter, attraversa il vettore di ListAdapter
     */
    private class Iterator implements HIterator {

        protected Vector<Object> vector;
        /**
         * Indice della posizione dell'iteratore
         */
        
        protected int index;
        /**
         * Variabile usata per iterare
         */
        protected boolean next;

        /**
         * Costruttore di Iterator dal vettore
         * 
         * @param v
         */
        public Iterator(Vector<Object> v) {
            this(v, 0);
        }

        /**
         * Costruttore di Iterator partendo dall'indice del vettore
         * 
         * @param v
         * @param index
         */
        public Iterator(Vector<Object> v, int index) {
            this.vector = v;
            this.index = index;
            next = false;
        }

        @Override
        public boolean hasNext() {
            return index < vector.size();
        }

        @Override
        public Object next() {
            if (index >= vector.size())
                throw new NoSuchElementException();
            next = true;
            return vector.elementAt(index++);
        }

        @Override
        public void remove() {
            if (!next)
                throw new IllegalStateException();
            next = false;
            vector.removeElement(--index);

        }

    }

    private class ListIterator extends Iterator implements HListIterator {
        /**
         * Variabile per iterare inversamente
         */
        protected boolean prev;

        /**
         * Costruttore di ListIterator dal vettore
         * 
         * @param v
         */
        public ListIterator(Vector<Object> v) {
            super(v);
            prev = false;
        }

        public ListIterator(Vector<Object> v, int index){
            super(v,index);
            prev = false;
        }

        @Override
        public void add(Object o) {
            if(o == null)
                throw new NullPointerException();
            vector.insertElementAt(o, index++);
            next = prev = false;
            
        }

        @Override
        public boolean hasNext(){
            return super.hasNext();
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public Object next(){
            if(!hasNext())
                throw new NoSuchElementException();
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
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int previousIndex() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public void set(Object o) {
            // TODO Auto-generated method stub
            
        }
    }

    private class SubList {

    }

    // Variabili
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

        if (c.isEmpty())
            return false;
        return true;
    }

    @Override
    public boolean addAll(int index, HCollection c) {
        if (c.isEmpty())
            return false;
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null)
            throw new NullPointerException();
        return vector.lastIndexOf(o);
    }

    @Override
    public HListIterator listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HListIterator listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
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
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(HCollection c) {
        // TODO Auto-generated method stub
        return false;
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
        // TODO Auto-generated method stub
        return null;
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