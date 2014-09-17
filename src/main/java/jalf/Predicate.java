package jalf;

import jalf.predicate.Eq;
import jalf.predicate.JavaPredicate;

/**
 * Tuple predicate, i.e. evaluating boolean expressions on tuples.
 */
public abstract class Predicate implements java.util.function.Predicate<Tuple> {

    /**
     * Factors a predicate from a native java one.
     *
     * @param fn a native java tuple predicate.
     * @return a proper Predicate instance for `fn`
     */
    public static Predicate java(java.util.function.Predicate<Tuple> fn) {
        return new JavaPredicate(fn);
    }

    /**
     * Factors a predicate checking for the equality of two values.
     *
     * @param left an attribute name or value 'literal'
     * @param right another attribute name or value 'literal'
     * @return a Predicate checking if left equals right on a tuple.
     */
    public static Predicate eq(Object left, Object right) {
        return new Eq(left, right);
    }

}
