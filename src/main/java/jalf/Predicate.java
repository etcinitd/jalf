package jalf;

import jalf.predicate.Among;
import jalf.predicate.And;
import jalf.predicate.Eq;
import jalf.predicate.False;
import jalf.predicate.Gt;
import jalf.predicate.Gte;
import jalf.predicate.JavaPredicate;
import jalf.predicate.Lt;
import jalf.predicate.Lte;
import jalf.predicate.Neq;
import jalf.predicate.Not;
import jalf.predicate.Or;
import jalf.predicate.True;
import jalf.util.Pair;

import java.util.Set;
import java.util.TreeSet;

/**
 * Tuple predicate, i.e. evaluating boolean expressions on tuples.
 */
public abstract class Predicate implements java.util.function.Predicate<Tuple> {

    public static final Predicate TRUE = True.instance();

    public static final Predicate FALSE = False.instance();

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
     * Factors a predicate checking that `attr` equals one of the values
     * specified in `values`.
     *
     * @param attr an attribute name.
     * @param values a set of values.
     * @return a Predicate checking if attr is among values.
     */
    public static Among among(AttrName attr, Iterable<?> values) {
        return new Among(attr, values);
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
     * Factors an AND predicate with `this` and `other`.
     */
    public Predicate and(Predicate other) {
        if (other == TRUE)
            return this;
        if (other == FALSE)
            return other;
        return new And(this, other);
    }

    /**
     * Factors an OR predicate with `this` and `other`.
     */
    public Predicate or(Predicate other) {
        if (other == TRUE)
            return other;
        if (other == FALSE)
            return this;
        return new Or(this, other);
    }

    /**
     * Factors a NOT predicate from `this`.
     */
    public Predicate not() {
        return new Not(this);
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

    public abstract void fillReferencedAttributes(Set<AttrName> attrNames);

    /**
     * Returns an equivalent predicate where attribute names have been renamed
     * according to `renaming`.
     *
     * @param renaming a renaming instance.
     * @return an equivalent predicate formulae where some renamings have been
     * applied.
     */
    public abstract Predicate rename(Renaming renaming);

    /**
     * Split this predicate in two, say pLeft and pRight, according to `list`.
     *
     * This method must guarantee that the splitting meets the following
     * conditions:
     *   - `this = pLeft & pRight`, i.e. this is a logical equivalent AND split
     *   - `pLeft` only makes references to attributes in `list`, but may
     *     reference a subset of them.
     *   - `pLeft` is as large as possible.
     *
     * This method is provided to help the optimizer rewrite expressions by
     * pushing restrictions as deep as possible, splitting them adequately
     * when facing binary operators. Observe that the split below is always
     * correct according to the specification above, but the third condition.
     * Subclasses are intended to implement smarter strategies, though, and to
     * ensure that the third condition is met too.
     *
     *  - pLeft = true
     *  - pRight = this
     *
     * @param list a list of attribute names.
     * @return two predicates, that meet the method specification.
     */
    public abstract Pair<Predicate> split(AttrList list);

}
