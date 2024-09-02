package com.dorvak.globallib.collections;

import com.dorvak.globallib.MultiThreading;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * A {@link ConcurrentHashMap} that removes entries after a certain amount of time.
 * @param <K>
 * @param <V>
 */
public class ExpirableConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V>  {

    private final Map<K, Long> timeMap = new ConcurrentHashMap<>();
    private final long expiryInMillis;
    private final Function<V, Boolean> precondition;

    public ExpirableConcurrentHashMap(long time, TimeUnit unit) {
        this(time, unit, (o) -> true);
    }

    public ExpirableConcurrentHashMap(long time, @NotNull TimeUnit unit, Function<V, Boolean> precondition) {
        Duration duration = Duration.of(time, unit.toChronoUnit());
        this.expiryInMillis = duration.toMillis();
        this.precondition = precondition;
        MultiThreading.scheduleRepeatingTask(this::clean, expiryInMillis, expiryInMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V put(@NotNull K key, @NotNull V value) {
        Date date = new Date();
        timeMap.put(key, date.getTime());
        return super.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            put(key, m.get(key));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V putIfAbsent(K key, V value) {
        if (!containsKey(key)) {
            return put(key, value);
        } else {
            return get(key);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(Object key) {
        V value = super.get(key);
        Date date = new Date();
        timeMap.put((K) key, date.getTime());
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        V value = super.getOrDefault(key, defaultValue);
        Date date = new Date();
        timeMap.put((K) key, date.getTime());
        return value;
    }

    /**
     * Cleans the map according to the expirations timestamps.
     */
    public void clean() {
        long currentTime = new Date().getTime();
        for (K key : timeMap.keySet()) {
            if (precondition.apply(super.get(key)) && currentTime > (timeMap.get(key) + expiryInMillis)) {
                remove(key);
                timeMap.remove(key);
            }
        }
    }
}
