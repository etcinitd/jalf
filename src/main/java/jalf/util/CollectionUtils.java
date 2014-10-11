package jalf.util;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.Collections.*;

/**
 * @author amirm
 */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... values) {
        return unmodifiableList(Arrays.asList(values));
    }

    @SafeVarargs
    public static <T> Set<T> setOf(T... values) {
        return unmodifiableSet(
                Stream.of(values).collect(Collectors.toSet()));
    }

    public static <K, V> Map<K, V> mapOf() {
        return emptyMap();
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        return unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        return unmodifiableMap(map);
    }

    public static <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
        Map<K, V> map = new HashMap<>();
        map.put(k1, v1);
        map.put(k2, v2);
        map.put(k3, v3);
        return unmodifiableMap(map);
    }

    public static <K,V,W> Map<K,W> remap(Map<K,V> map, BiFunction<K,V,W> remapper) {
        Map<K,W> remapped = new ConcurrentHashMap<>();
        map.forEach((k,v) -> {
            W newVal = remapper.apply(k, v);
            remapped.put(k, newVal);
        });
        return remapped;
    }

    public static <K,V,L> Map<L,V> rekey(Map<K,V> map, BiFunction<K,V,L> rekeyer) {
        Map<L,V> rekeyed = new ConcurrentHashMap<>();
        map.forEach((k,v) -> {
            L newKey = rekeyer.apply(k, v);
            rekeyed.put(newKey, v);
        });
        return rekeyed;
    }

    public static <E> ConcurrentHashSet<E> newConcurrentHashSet() {
        return new ConcurrentHashSet<>();
    }

    public static <T> Stream<T> streamOf(Iterable<T> it) {
        return StreamSupport.stream(it.spliterator(), false);
    }

    public static <T> Stream<T> parallelStreamOf(Iterable<T> it) {
        return StreamSupport.stream(it.spliterator(), true);
    }}
