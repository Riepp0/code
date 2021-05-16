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
    private class Iterator implements HIterator {
        protected Vector v;
        protected int index;
        protected boolean next;

        public Iterator(int i) {
            v = vector;
            index = i;
            next = false;
        }

        public Iterator() {
            this(0);
        }

        @Override
        public boolean hasNext() {
            return index < vector.size();
        }

        @Override
        public Object next() {
            if (!hasNext())
                throw new NoSuchElementException();
            next = true;
            return vector.elementAt(index++);
        }

        @Override
        public void remove() {
            if (!next)
                throw new IllegalStateException();
            vector.removeElementAt(--index);
            next = false;
        }
    }

    private class ListIterator extends Iterator implements HListIterator {
        protected boolean prev;

        public ListIterator(int i) {
            super(i);
            prev = false;
        }

        public ListIterator() {
            this(0);
        }

        @Override
        public void add(Object o) {
            if (o == null)
                throw new NullPointerException();
            vector.insertElementAt(o, index++);
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
            if (!hasNext())
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
            if (!hasPrevious())
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
        public void remove() {
            if (!next && !prev)
                throw new IllegalStateException();
            else if (prev) {
                vector.removeElementAt(index);
                prev = next = false;
            } else {
                super.remove();
            }
        }

        @Override
        public void set(Object o) {
            if (o == null)
                throw new NullPointerException();
            if (vector.isEmpty()) {
                add(o);
                return;
            }
            if (!next && !prev)
                throw new IllegalStateException();
            else if (next)
                vector.setElementAt(o, index - 1);
            else if (prev)
                vector.setElementAt(o, index);
        }

    }

    private class SubList extends ListAdapter {
        private class SubListIterator implements HIterator {
            protected Vector v;
            protected int index;
            protected boolean next;

            public SubListIterator(int i) {
                v = ListAdapter.this.vector;
                index = ListAdapter.SubList.this.from + i;
                next = false;
            }

            public SubListIterator() {
                this(0);
            }

            @Override
            public boolean hasNext() {
                return index < ListAdapter.SubList.this.to;
            }

            @Override
            public Object next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                next = true;
                return v.elementAt(index++);
            }

            @Override
            public void remove() {
                if (!next)
                    throw new IllegalStateException();
                v.removeElementAt(--index);
                next = false;
                ListAdapter.SubList.this.to--;
            }
        }

        private class SubListListIterator extends SubListIterator implements HListIterator {
            protected boolean prev;

            public SubListListIterator(int i) {
                super(i);
                prev = false;
            }

            public SubListListIterator() {
                this(0);
            }

            @Override
            public void add(Object o) {
                if (o == null)
                    throw new NullPointerException();
                v.insertElementAt(o, index++);
                next = prev = false;
                ListAdapter.SubList.this.to++;
            }

            @Override
            public boolean hasNext() {
                return super.hasNext();
            }

            @Override
            public boolean hasPrevious() {
                return index > ListAdapter.SubList.this.from;
            }

            @Override
            public Object next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                next = true;
                prev = false;
                return super.next();
            }

            @Override
            public int nextIndex() {
                return index - ListAdapter.SubList.this.from;
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
                return index - ListAdapter.SubList.this.from - 1;
            }

            @Override
            public void remove() {
                if (!next && !prev)
                    throw new IllegalStateException();
                else if (prev) {
                    v.removeElementAt(index);
                    prev = next = false;
                    ListAdapter.SubList.this.to--;
                } else
                    super.remove();
            }

            @Override
            public void set(Object o) {
                if (o == null)
                    throw new NullPointerException();
                if (from == to) {
                    add(o);
                    return;
                }
                if (!next && !prev)
                    throw new IllegalStateException();
                else if (next)
                    v.setElementAt(o, index - 1);
                else if (prev)
                    v.setElementAt(o, index);
            }

        }

        protected int from, to;

        public SubList(int f, int t) {
            from = f;
            to = t;
        }

        @Override
        public void add(int index, Object element) {
            if (element == null)
                throw new NullPointerException();
            if (index < 0 || index > size())
                throw new IndexOutOfBoundsException();
            ListAdapter.this.add(index + from, element);
            to++;
        }

        @Override
        public boolean add(Object o) {
            if (o == null)
                throw new NullPointerException();
            ListAdapter.this.add(to++, o);
            return true;
        }

        @Override
        public boolean addAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            int old = size();
            ListAdapter.this.addAll(to, c);
            to += c.size();
            return old != size();
        }

        @Override
        public boolean addAll(int index, HCollection c) {
            if (c == null)
                throw new NullPointerException();
            if (index < 0 || index > size())
                throw new IndexOutOfBoundsException();
            int old = size();
            ListAdapter.this.addAll(from + index, c);
            to += c.size();
            return old != size();
        }

        @Override
        public void clear() {
            while (to > from)
                remove(to - from - 1);
        }

        @Override
        public boolean contains(Object o) {
            if (o == null)
                throw new NullPointerException();
            if (isEmpty())
                return false;
            for (int i = 0; i < size(); i++) {
                if (ListAdapter.this.get(i + from).equals(o))
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
            if (o == null)
                return false;
            if (!(o instanceof HList))
                return false;
            HList cmp = (HList) o;
            if (cmp.size() != size())
                return false;
            HIterator iter = iterator();
            HIterator itera = cmp.iterator();
            while (iter.hasNext()) {
                if (!iter.next().equals(itera.next()))
                    return false;
            }
            return true;
        }

        @Override
        public Object get(int index) {
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            return ListAdapter.this.get(from + index);
        }

        @Override
        public int hashCode() {
            int hashCode = 1;
            HIterator i = iterator();
            while (i.hasNext()) {
                Object obj = i.next();
                hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
            }
            return hashCode;
        }

        @Override
        public int indexOf(Object o) {
            if (o == null) {
                throw new NullPointerException();
            }
            for (int i = from; i < to; i++) {
                if (ListAdapter.this.get(i).equals(o))
                    return i - from;
            }
            return -1;
        }

        @Override
        public boolean isEmpty() {
            return size() == 0;
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
                if (ListAdapter.this.get(i).equals(o))
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
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            return new SubListListIterator(index);
        }

        @Override
        public Object remove(int index) {
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            to--;
            return ListAdapter.this.remove(index + from);
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
            int old = size();
            HIterator iter = c.iterator();
            while (iter.hasNext()) {
                Object rem = iter.next();
                if (contains(rem))
                    remove(rem);
            }
            return old != size();
        }

        @Override
        public boolean retainAll(HCollection c) {
            if (c == null)
                throw new NullPointerException();
            int old = size();
            HIterator iter = iterator();
            while (iter.hasNext())
                if (!c.contains(iter.next()))
                    iter.remove();
            return old != size();
        }

        @Override
        public Object set(int index, Object element) {
            if (index < 0 || index >= size())
                throw new IndexOutOfBoundsException();
            if (element == null)
                throw new NullPointerException();
            Object ret = ListAdapter.this.get(index + from);
            ListAdapter.this.set(index + from, element);
            return ret;
        }

        @Override
        public int size() {
            return to - from;
        }

        @Override
        public HList subList(int from, int to) {
            if (from < 0 || to > size() || from > to)
                throw new IndexOutOfBoundsException();
            return new SubList(from, to);
        }

        @Override
        public Object[] toArray() {
            Object[] ret = new Object[size()];
            for (int i = from; i < to; i++) {
                ret[i - from] = ListAdapter.this.get(i);
            }
            return ret;
        }

        @Override
        public Object[] toArray(Object[] a) {
            if (a == null)
                throw new NullPointerException();
            if (a.length < size())
                a = new Object[size()];
            for (int i = 0; i < size(); i++) {
                a[i] = get(i);
            }
            return a;
        }
    }

    protected Vector vector;

    public ListAdapter() {
        vector = new Vector();
    }

    @Override
    public void add(int index, Object element) {
        if (element == null)
            throw new NullPointerException();
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        vector.insertElementAt(element, index);
    }

    @Override
    public boolean add(Object o) {
        add(size(), o);
        return true;
    }

    @Override
    public boolean addAll(HCollection c) {
        return addAll(size(), c);
    }

    @Override
    public boolean addAll(int index, HCollection c) {
        if (c == null)
            throw new NullPointerException();
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        int old = size();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            add(index++, iter.next());
        return old != size();
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
        while (iter.hasNext()) {
            if (!vector.contains(iter.next()))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (!(o instanceof HList))
            return false;
        HList cmp = (HList) o;
        if (cmp.size() != size())
            return false;
        HIterator iter = iterator();
        HIterator itera = cmp.iterator();
        while (iter.hasNext()) {
            if (!iter.next().equals(itera.next()))
                return false;
        }
        return true;
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        return vector.elementAt(index);
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        HIterator i = iterator();
        while (i.hasNext()) {
            Object obj = i.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }
        return hashCode;
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
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        return new ListIterator(index);
    }

    @Override
    public Object remove(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException();
        Object ret = vector.elementAt(index);
        vector.removeElementAt(index);
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
        int old = size();
        HIterator iter = c.iterator();
        while (iter.hasNext())
            remove(iter.next());
        return old != size();
    }

    @Override
    public boolean retainAll(HCollection c) {
        if (c == null)
            throw new NullPointerException();
        int old = size();
        HIterator iter = iterator();
        while (iter.hasNext()) {
            if (!c.contains(iter.next()))
                iter.remove();
        }
        return old != size();
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
    public HList subList(int from, int to) {
        if (from < 0 || to > size() || from > to)
            throw new IndexOutOfBoundsException();
        return new SubList(from, to);
    }

    @Override
    public Object[] toArray() {
        Object[] ret = new Object[size()];
        vector.copyInto(ret);
        return ret;
    }

    @Override
    public Object[] toArray(Object[] a) {
        if (a == null)
            throw new NullPointerException();
        if (a.length < size())
            a = new Object[size()];
        vector.copyInto(a);
        return a;
    }
}