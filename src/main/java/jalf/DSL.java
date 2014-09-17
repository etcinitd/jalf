package jalf;

import jalf.predicate.Eq;
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

    public static Predicate eq(Object left, Object right) {
        return new Eq(left, right);
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

    public static <T> Relation restrict(Relation relation, TypedAttrName<T> name, java.util.function.Predicate<T> fn) {
        @SuppressWarnings("unchecked")
        java.util.function.Predicate<Tuple> tfn = (t -> fn.test((T) t.get(name)));
        return restrict(relation, tfn);
    }
}
