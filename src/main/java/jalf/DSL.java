package jalf;

import java.util.stream.Stream;

import jalf.aggregator.Aggregator;
import jalf.aggregator.Avg;
import jalf.aggregator.Count;
import jalf.aggregator.Max;
import jalf.relation.materialized.SetMemoryRelation;
import jalf.type.Heading;
import jalf.type.RelationType;

/**
 * @author amirm
 */
public class DSL {

    public static final Predicate TRUE = Predicate.TRUE;

    public static final Predicate FALSE = Predicate.FALSE;

    public static final Relation DUM = Relation.DUM;

    public static final Relation DEE = Relation.DEE;

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

    // Heading and Types

    public static Heading heading(Object... pairs) {
        return Heading.dress(pairs);
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
        return Predicate.among(val, vals);
    }

    public static Predicate eq(Object left, Object right) {
        return Predicate.eq(left, right);
    }

    public static Predicate neq(Object left, Object right) {
        return Predicate.neq(left, right);
    }

    public static Predicate gt(Comparable<?> left, Comparable<?> right) {
        return Predicate.gt(left, right);
    }

    public static Predicate gte(Comparable<?> left, Comparable<?> right) {
        return Predicate.gte(left, right);
    }

    public static Predicate lt(Comparable<?> left, Comparable<?> right) {
        return Predicate.lt(left, right);
    }

    public static Predicate lte(Comparable<?> left, Comparable<?> right) {
        return Predicate.lte(left, right);
    }

    // Tuple

    public static Tuple tuple(Object... keyValuePairs) {
        return Tuple.dress(keyValuePairs);
    }

    // Relation

    public static Relation relation(RelationType type, Tuple... tuples) {
        return SetMemoryRelation.tuples(type, tuples);
    }

    public static Relation relation(Heading heading, Tuple... tuples) {
        return relation(RelationType.dress(heading), tuples);
    }

    public static Relation relation(Tuple... tuples) {
        return relation(RelationType.infer(Stream.of(tuples)), tuples);
    }

    // Relational algebra

    public static Relation select(Relation relation, Selection selection) {
        return relation.select(selection);
    }

    public static Relation project(Relation relation, AttrList attrNames) {
        return relation.project(attrNames);
    }

    public static Relation summarize(Relation relation, AttrList by, Aggregator<?> agg, AttrName as ){
        return relation.summarize(by,agg,as);
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

    public static Relation join(Relation left, Relation right) {
        return left.join(right);
    }

    public static Relation union(Relation left, Relation right) {
        return left.union(right);
    }

    public static Relation intersect(Relation left, Relation right) {
        return left.intersect(right);
    }

    public static Relation minus(Relation left, Relation right) {
        return left.minus(right);
    }

    // Aggregator

    public static Count count(){
        return Count.count();
    }

    public static Max max(AttrName attr) {
        return Max.max(attr);
    }

    public static Avg avg(AttrName attr) {
        return Avg.avg(attr);
    }

}
