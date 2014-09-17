package jalf;

import jalf.relation.algebra.Eq;
import jalf.relation.algebra.Predicate;
import jalf.relation.materialized.SetMemoryRelation;

/**
 * @author amirm
 */
public class DSL {

    // AttrName

    public static AttrName attr(String attr){
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

    public static Predicate eq(AttrName attrName, Object value) {
        return new Eq(attrName, value);
    }

    public static Predicate eq(String attrName, Object value) {
        return eq(AttrName.attr(attrName), value);
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

}
