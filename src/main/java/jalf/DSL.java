package jalf;

import jalf.predicate.*;
import jalf.relation.materialized.SetMemoryRelation;

/**
 * @author amirm
 */
public class DSL {

    // AttrName

    public static AttrName attr(String attr) {
        return AttrName.attr(attr);
    }

    // AttrList

    public static AttrList attrs(AttrName... attrNames) {
        return AttrList.attrs(attrNames);
    }

    public static AttrList attrs(String... attrNames) {
        return AttrList.attrs(attrNames);
    }

    // Renaming

    public static Renaming renaming(AttrName... namePairs) {
        return Renaming.extension(namePairs);
    }

    public static Renaming renaming(String... namePairs) {
        return Renaming.extension(namePairs);
    }

    // Predicate

    public static Predicate among(AttrName val, Iterable<?> vals) {
        return new Among(val, vals);
    }

    public static Predicate eq(Object left, Object right) {
        return new Eq(left, right);
    }

    //TODO Is there a clean way to make Comparable predicates more Java type-safe?

    @SuppressWarnings("unchecked")
    public static Predicate gt(Comparable<?> left, Comparable<?> right) {
        return new Gt((Comparable<Object>) left, (Comparable<Object>) right);
    }

    @SuppressWarnings("unchecked")
    public static Predicate gte(Comparable<?> left, Comparable<?> right) {
        return new Gte((Comparable<Object>) left, (Comparable<Object>) right);
    }

    @SuppressWarnings("unchecked")
    public static Predicate lt(Comparable<?> left, Comparable<?> right) {
        return new Lt((Comparable<Object>) left, (Comparable<Object>) right);
    }

    @SuppressWarnings("unchecked")
    public static Predicate lte(Comparable<?> left, Comparable<?> right) {
        return new Lte((Comparable<Object>) left, (Comparable<Object>) right);
    }

    public static Predicate neq(Object left, Object right) {
        return new Neq(left, right);
    }

    // Tuple

    public static Tuple tuple(Object... keyValuePairs) {
        return Tuple.varargs(keyValuePairs);
    }

    // Relation

    public static Relation relation(Tuple... tuples) {
        return new SetMemoryRelation(tuples);
    }

    // Relational algebra

    public static Relation project(Relation relation, AttrList attrNames) {
        return relation.project(attrNames);
    }

    public static Relation rename(Relation relation, Renaming renaming) {
        return relation.rename(renaming);
    }

    public static Relation restrict(Relation relation, Predicate predicate) {
        return relation.restrict(predicate);
    }

    public static Relation restrict(Relation relation, java.util.function.Predicate<Tuple> fn) {
        return restrict(relation, Predicate.java(fn));
    }
}
