package jalf.util;

import jalf.RelAttr;
import jalf.Relation;
import jalf.Tuple;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableMap;
import static java.util.stream.Collectors.toList;

/**
 * @author amirm
 */
public final class Utils {
    private Utils() {
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... values) {
        return unmodifiableList(Arrays.asList(values));
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

    public static List<RelAttr> attrs(String... attrs) {
        return unmodifiableList(
                Stream.of(attrs)
                        .distinct()
                        .map(RelAttr::new)
                        .collect(Collectors.toList())
        );
    }

    public static Tuple tuple(Object... keyValuePairs) {
        return new Tuple(keyValuePairs);
    }

    public static Relation relation(Tuple... tuples) {
        return new Relation(tuples);
    }
}
