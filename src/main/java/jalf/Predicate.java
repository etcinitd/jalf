package jalf;

import jalf.predicate.Eq;
import jalf.predicate.Gt;
import jalf.predicate.Gte;
import jalf.predicate.JavaPredicate;
import jalf.predicate.Lt;
import jalf.predicate.Lte;
import jalf.predicate.Neq;

import java.util.Set;
import java.util.TreeSet;

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

    /**
     * Factors a predicate checking for the inequality of two values.
     *
     * @param left an attribute name or value 'literal'
     * @param right another attribute name or value 'literal'
     * @return a Predicate checking if left is not equal to right on a tuple.
     */
    public static Predicate neq(Object left, Object right) {
        return new Neq(left, right);
    }

    //TODO Is there a clean way to make Comparable predicates more Java type-safe?

    /**
     * Factors a predicate checking if left is greater than right.
     *
     * @param left an attribute name or value 'literal'
     * @param right another attribute name or value 'literal'
     * @return a Predicate checking if left is greater than right on a tuple.
     */
    @SuppressWarnings("unchecked")
    public static Predicate gt(Comparable<?> left, Comparable<?> right) {
        return new Gt((Comparable<Object>) left, (Comparable<Object>) right);
    }

    /**
     * Factors a predicate checking if left is greater or equal to right.
     *
     * @param left an attribute name or value 'literal'
     * @param right another attribute name or value 'literal'
     * @return a Predicate checking if left is greater or equal to right on a tuple.
     */
    @SuppressWarnings("unchecked")
    public static Predicate gte(Comparable<?> left, Comparable<?> right) {
        return new Gte((Comparable<Object>) left, (Comparable<Object>) right);
    }

    /**
     * Factors a predicate checking if left is lesser than right.
     *
     * @param left an attribute name or value 'literal'
     * @param right another attribute name or value 'literal'
     * @return a Predicate checking if left is lesser than right on a tuple.
     */
    @SuppressWarnings("unchecked")
    public static Predicate lt(Comparable<?> left, Comparable<?> right) {
        return new Lt((Comparable<Object>) left, (Comparable<Object>) right);
    }

    /**
     * Factors a predicate checking if left is lesser or equal to right.
     *
     * @param left an attribute name or value 'literal'
     * @param right another attribute name or value 'literal'
     * @return a Predicate checking if left is lesser  or equal to right on a tuple.
     */
    @SuppressWarnings("unchecked")
    public static Predicate lte(Comparable<?> left, Comparable<?> right) {
        return new Lte((Comparable<Object>) left, (Comparable<Object>) right);
    }

    /**
     * Checks whether this predicate fully supports static analysis.
     *
     * @return true if this predicate can be fully analyzed statically, false
     * otherwise.
     */
    public boolean isStaticallyAnalyzable() {
        return true;
    }

    /**
     * Returns the list of attribute names referenced by this predicate.
     *
     * @return a list of all attribute names referenced by this predicate.
     * @post When the predicate is fully analyzable statically, the returned set
     * must include all attribute names referenced by the predicate. Otherwise,
     * it may return a subset of them.
     */
    public AttrList getReferencedAttributes() {
        Set<AttrName> attrNames = new TreeSet<AttrName>();
        fillReferencedAttributes(attrNames);
        return AttrList.attrs(attrNames);
    }

    protected abstract void fillReferencedAttributes(Set<AttrName> attrNames);

}
