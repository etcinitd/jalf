package jalf;

import jalf.relation.algebra.Eq;
import jalf.relation.algebra.Predicate;
import jalf.relation.materialized.SetMemoryRelation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author amirm
 */
public class DSL {

    public static AttrList attrs(String... attrs) {
        SortedSet<AttrName> set = Stream.of(attrs)
            .distinct()
            .map(AttrName::attr)
            .collect(Collectors.toCollection(TreeSet::new));
        return new AttrList(set);
    }

    public static Renaming renaming(String... namePairs) {
        List<AttrName> attrNames = Stream.of(namePairs)
            .map(AttrName::attr)
            .collect(Collectors.toList());
        Map<AttrName, AttrName> renamingMap = new HashMap<>();
        for (int i = 0; i < attrNames.size(); i++) {
            AttrName currentName = attrNames.get(i++);
            AttrName mappedName = attrNames.get(i);
            renamingMap.put(currentName, mappedName);
        }
        return new Renaming(renamingMap);
    }

    public static Tuple tuple(Object... keyValuePairs) {
        return new Tuple(keyValuePairs);
    }

    public static Relation relation(Tuple... tuples) {
        return new SetMemoryRelation(tuples);
    }

    public static Relation project(Relation relation, AttrList attrNames) {
        return relation.project(attrNames);
    }

    public static Relation rename(Relation relation, Renaming renaming) {
        return relation.rename(renaming);
    }

    public static Relation restrict(Relation relation, Predicate predicate) {
        return relation.restrict(predicate);
    }

    public static Predicate eq(String attrName, Object value) {
        return new Eq(AttrName.attr(attrName), value);
    }
}
