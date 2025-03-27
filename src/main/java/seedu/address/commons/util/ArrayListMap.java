package seedu.address.commons.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A Map implementation backed by ArrayLists and compares using Object#equals.
 *
 * @param <K> The type of the keys in the map.
 * @param <V> The type of the values in the map.
 */
public class ArrayListMap<K, V> implements Map<K, V> {
    private final ArrayListSet<K> keys;
    private final ArrayList<V> vals;

    /**
     * Creates a new empty ArrayListMap.
     */
    public ArrayListMap() {
        this.keys = new ArrayListSet<>();
        this.vals = new ArrayList<>();
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
    public boolean containsKey(Object key) {
        return keys.contains(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return vals.contains(value);
    }

    @Override
    public V get(Object key) {
        int index = keys.indexOf(key);
        if (index == -1) {
            return null;
        }
        return vals.get(index);
    }

    public V get(int index) {
        return vals.get(index);
    }

    @Override
    public V put(K key, V value) {
        int index = keys.indexOf(key);
        if (index == -1) {
            keys.add(key);
            vals.add(value);
            return null;
        }
        return vals.set(index, value);
    }

    @Override
    public V remove(Object key) {
        int index = keys.indexOf(key);
        if (index == -1) {
            return null;
        }
        V val = vals.remove(index);
        keys.remove(index);
        return val;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (var entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        keys.clear();
        vals.clear();
    }

    @Override
    public Set<K> keySet() {
        return keys;
    }

    @Override
    public Collection<V> values() {
        return vals;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        for (int i = 0; i < keys.size(); i++) {
            set.add(Map.entry(keys.get(i), vals.get(i)));
        }
        return set;
    }
}
