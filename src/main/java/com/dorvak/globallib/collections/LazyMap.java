package com.dorvak.globallib.collections;

import java.util.HashMap;
import java.util.function.Function;

/**
 * LazyMap is a map that generates values on the fly when they are requested.
 * @param <K> Key
 * @param <V> Value
 */
public class LazyMap<K, V> extends HashMap<K, V> {

    private final Function<K, V> generator;

    /**
     * Constructor
     * @param generator Function to generate values
     */
    public LazyMap(Function<K, V> generator) {
        super();
        this.generator = generator;
    }

    @Override
    public V get(Object key) {
        if (!containsKey(key)) {
            put((K) key, generator.apply((K) key));
        }
        return super.get(key);
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return get(key);
    }
}
