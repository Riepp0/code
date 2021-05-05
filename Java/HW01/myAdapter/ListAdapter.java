package myAdapter;

import java.util.NoSuchElementException;
import java.util.Vector;

public class ListAdapter implements HList {
    // Sottoclassi

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
         * Costruttore di Iterator partendo dall'indice del vettore
         * 
         * @param v
         * @param index
         */
        public Iterator(Vector<Object> v, int index) {
            if(index < 0 || index > size())
                throw new IllegalArgumentException();
            vector = v;
            this.index = index;
            next = false;
        }

        /**
         * Costruttore di Iterator dal vettore
         * 
         * @param v
         */
        public Iterator(Vector<Object> v) {
            this(v,0);
        }


        @Override
        public boolean hasNext() {
            return index < vector.size()-1;
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

        /**
         * Costruttore di ListIterator dato il vettore e l'indice da cui partire
         * 
         * @param v
         * @param index
         */
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
            if(!hasPrevious())
                throw new NoSuchElementException();
            next = false;
            prev = true;
            return vector.elementAt(--index);
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove(){
            if(!next && !prev)
                throw new IllegalStateException();
            if(prev){
                vector.removeElementAt(index);
                prev = next = false;
            }
            else super.remove();
        }

        @Override
        public void set(Object o) {
            if(o == null)
                throw new NullPointerException();
            if(vector.isEmpty())
                add(o);
            if(!next && !prev)
                throw new IllegalStateException();
            if(next)
                vector.setElementAt(o, index-1);
            if(prev)
                vector.setElementAt(o, index);
        }
    }

    private class SubList implements HList{
        /**
         *  Lista
         */
        private ListAdapter list;
        /**
         *  Indica l'indice più basso
         */
        private int from;

        /**
         * Indica l'indice più alto
         */
        private int to;

        /**
         * Costruttore a partire dagli indici
         * 
         * @param f
         * @param t
         */
        public SubList(ListAdapter l,int f, int t){
            if(f < 0 || t >= list.size())
                throw new IllegalArgumentException();
            list = l;
            from = f;
            to = t;
        }

        @Override
        public void add(int index, Object element) {
            // TODO Auto-generated method stub
            
        }

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
        public boolean addAll(int index, HCollection c) {
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
        public Object get(int index) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int indexOf(Object o) {
            // TODO Auto-generated method stub
            return 0;
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
        public int lastIndexOf(Object o) {
            // TODO Auto-generated method stub
            return 0;
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
        public Object set(int index, Object element) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int size() {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public HList subList(int fromIndex, int toIndex) {
            // TODO Auto-generated method stub
            return null;
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
        if(c == null)
            throw new NullPointerException();
        HIterator iter = c.iterator();
        while(iter.hasNext())
            if(!vector.contains(iter.next()))
                return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if(this.getClass().isInstance(o))
            return false;
        ListAdapter tmp = (ListAdapter) o;
        if(tmp.size() != size())
            return false;
        HIterator iter = tmp.iterator();
        HIterator itera = iterator();
        while(itera.hasNext())
            if(!itera.next().equals(iter.next()))
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
        return new Iterator(vector);
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null)
            throw new NullPointerException();
        return vector.lastIndexOf(o);
    }

    @Override
    public HListIterator listIterator() {
        return new ListIterator(vector);
    }

    @Override
    public HListIterator listIterator(int index) {
        if (index < 0 || index > vector.size())
            throw new IndexOutOfBoundsException();
        return new ListIterator(vector, index);
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
        if(c == null)
            throw new NullPointerException();
        HIterator iter = c.iterator();
        while(iter.hasNext())
            remove(iter.next());
        return true;
    }

    @Override
    public boolean retainAll(HCollection c) {
        if(c == null)
            throw new NullPointerException();
        HIterator iter = iterator();
        while(iter.hasNext())
            if(!c.contains(iter.next()))
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