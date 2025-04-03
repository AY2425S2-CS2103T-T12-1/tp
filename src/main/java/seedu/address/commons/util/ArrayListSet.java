package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * A Set implementation backed by ArrayLists and compares using Object#equals.
 * @param <K> The type of the keys in the set.
 */
public class ArrayListSet<K> implements Set<K> {
    private final ArrayList<K> keys;

    public ArrayListSet() {
        this.keys = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return keys.contains(o);
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }

    @Override
    public Object[] toArray() {
        return keys.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return keys.toArray(a);
    }

    @Override
    public boolean add(K k) {
        if (contains(k)) {
            return false;
        }
        return keys.add(k);
    }

    @Override
    public boolean remove(Object o) {
        return keys.remove(o);
    }

    public void set(K k, K l) {
        int index = indexOf(k);
        keys.set(index, l);
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param index the index of the element to be removed
     * @return true
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public boolean remove(int index) {
        keys.remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return keys.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends K> c) {
        boolean changed = false;
        for (K k : c) {
            changed |= add(k);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return keys.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return keys.removeAll(c);
    }

    @Override
    public void clear() {
        keys.clear();
    }

    public int indexOf(Object o) {
        return keys.indexOf(o);
    }

    public K get(int index) {
        return keys.get(index);
    }
}
