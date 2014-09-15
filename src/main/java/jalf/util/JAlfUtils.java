package jalf.util;

import jalf.AttrName;
import jalf.Relation;
import jalf.Tuple;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;

/**
 * @author amirm
 */
public class JAlfUtils {
    public static Set<AttrName> attrs(String... attrs) {
        return unmodifiableSet(
                Stream.of(attrs)
                        .distinct()
                        .map(AttrName::attr)
                        .collect(Collectors.toSet())
        );
    }

    public static Tuple tuple(Object... keyValuePairs) {
        return new Tuple(keyValuePairs);
    }

    public static Relation relation(Tuple... tuples) {
        return new Relation(tuples);
    }
}
