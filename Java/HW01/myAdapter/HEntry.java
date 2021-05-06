package myAdapter;

public interface HEntry {
    /**
     * Compares the specified object with this entry for equality. Returns true if
     * the given object is also a map entry and the two entries represent the same
     * mapping. More formally, two entries e1 and e2 represent the same mapping if
     * (e1.getKey()==null ? e2.getKey()==null : e1.getKey().equals(e2.getKey())) &&
     * (e1.getValue()==null ? e2.getValue()==null :
     * e1.getValue().equals(e2.getValue()))
     * 
     * This ensures that the equals method works properly across different
     * implementations of the Map.Entry interface.
     * 
     * @param o - object to be compared for equality with this map entry.
     * @return true if the specified object is equal to this map entry.
     * @see Object.hashCode(), Hashtable
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the key corresponding to this entry.
     * 
     * @return the key corresponding to this entry.
     */
    Object getKey();

    /**
     * Returns the value corresponding to this entry. If the mapping has been
     * removed from the backing map (by the iterator's remove operation), the
     * results of this call are undefined.
     * 
     * @return the value corresponding to this entry.
     */
    Object getValue();

    /**
     * Returns the hash code value for this map entry. The hash code of a map entry
     * e is defined to be: (e.getKey()==null ? 0 : e.getKey().hashCode()) ^
     * (e.getValue()==null ? 0 : e.getValue().hashCode())
     * 
     * This ensures that e1.equals(e2) implies that e1.hashCode()==e2.hashCode() for
     * any two Entries e1 and e2, as required by the general contract of
     * Object.hashCode.
     * 
     * @return the hash code value for this map entry.
     * @see Object.hashCode(), Object.equals(Object), equals(Object)
     */
    @Override
    int hashCode();

    /**
     * Replaces the value corresponding to this entry with the specified value
     * (optional operation). (Writes through to the map.) The behavior of this call
     * is undefined if the mapping has already been removed from the map (by the
     * iterator's remove operation).
     * 
     * @param value - new value to be stored in this entry.
     * @return old value corresponding to the entry.
     * @throws UnsupportedOperationException - if the put operation is not supported
     *                                       by the backing map.
     * @throws ClassCastException            - if the class of the specified value
     *                                       prevents it from being stored in the
     *                                       backing map.
     * @throws IllegalArgumentException      - if some aspect of this value prevents
     *                                       it from being stored in the backing
     *                                       map.
     * @throws NullPointerException          - the backing map does not permit null
     *                                       values, and the specified value is
     *                                       null.
     */
    Object setValue(Object value);
}