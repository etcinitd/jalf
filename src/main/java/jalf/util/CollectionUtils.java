package jalf.util;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static <E> ConcurrentHashSet<E> newConcurrentHashSet() {
        return new ConcurrentHashSet<>();
    }
}
