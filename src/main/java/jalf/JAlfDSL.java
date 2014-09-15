package jalf;

import jalf.AttrName;
import jalf.Relation;
import jalf.Tuple;
import jalf.util.ValidationUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jalf.util.ValidationUtils.validate;
import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

/**
 * @author amirm
 */
public class JAlfDSL {
    public static Set<AttrName> attrs(String... attrs) {
        return unmodifiableSet(
                Stream.of(attrs)
                        .distinct()
                        .map(AttrName::attr)
                        .collect(Collectors.toSet())
        );
    }

    public static Map<AttrName, AttrName> renaming(String... namePairs) {
        List<AttrName> attrNames = Stream.of(namePairs)
                .map(AttrName::attr)
                .collect(Collectors.toList());
        Map<AttrName, AttrName> renamingMap = new HashMap<>();
        for (int i = 0; i < attrNames.size(); i++) {
            AttrName currentName = attrNames.get(i++);
            AttrName mappedName = attrNames.get(i);
            renamingMap.put(currentName, mappedName);
        }
        return unmodifiableMap(renamingMap);
    }

    public static Tuple tuple(Object... keyValuePairs) {
        return new Tuple(keyValuePairs);
    }

    public static Relation relation(Tuple... tuples) {
        return new Relation(tuples);
    }

    // TODO What about passing a Stream?
    public static Relation project(Relation relation, Set<AttrName> attrNames) {
        // TODO implement
        return relation;
    }

    public static Relation rename(Relation relation, Map<AttrName, AttrName> renaming) {
        // TODO implement
        return relation;
    }
}
