package jalf;

import java.util.stream.Stream;

import jalf.aggregator.Aggregator;
import jalf.relation.algebra.Dee;
import jalf.relation.algebra.Dum;
import jalf.type.RelationType;
import jalf.type.TupleType;

/**
 * A relation, aka a typed set of typed tuples/facts plus algebra.
 *
 * This interface is the main interface over all relation kinds in Jalf. Some
 * relations are represented as in-memory sets of tuples, while others may
 * denote data in files, or in relational/SQL databases; still others denote
 * the result of evaluating algebra expressions, and so on. JAlf guarantees
 * that all those relations can be used together in relational expressions.
 * Algebraic expressions are evaluated lazily when a terminal method is
 * invoked, in a similar way to Java's streams.
 *
 * This interface captures the public interface on JAlf's relations and their
 * algebra. It is not intended to be implemented directly, please subclass
 * `AbstractRelation` instead. The latter provides the protected API that is
 * needed for JAlf' compiler infrastructure to work.
 */
public interface Relation {

    /** Dum, the relation with no attributes and no tuple. */
    Relation DUM = Dum.instance();

    /** Dee, the relation with no attributes but one tuple. */
    Relation DEE = Dee.instance();

    /**
     * Returns the type of this relation.
     *
     * @return the type of the relation.
     */
    RelationType getType();

    /**
     * Returns the type of the tuples belonging to this relation.
     *
     * @return the type of the relation's tuples
     */
    TupleType getTupleType();

    Relation select(Selection selection);

    /**
     * Projects this relation on a subset of its attributes.
     *
     * @pre attributes must be a subset of relation's attribute names.
     * @param attributes the list of attributes to project on.
     * @return the resulting relation.
     */
    Relation project(AttrList attributes);

    /**
     * Summarize this relation on a subset of its attributes.
     *
     * @pre attributes must be a subset of relation's attribute names.
     * @param attributes the list of attributes to summarize by.
     * @param un aggregat
     * @param nom de la colonne de l'aggrgegat as
     * @return the resulting relation.
     */
    Relation summarize(AttrList by,Aggregator<?> agg,AttrName as);

    /**
     * Rename some attributes of this relation.
     *
     * @pre source attribute names must belong to relation's attributes. target
     * attributes names must not.
     * @param renaming a map of source -> target attribute renamings.
     * @return the resulting relation.
     */
    Relation rename(Renaming renaming);

    /**
     * Joins this relation with `right` one.
     *
     * @param right another relation.
     * @return the resulting relation.
     */
    Relation join(Relation right);

    /**
     * Union this relation with `right` one.
     *
     * @param right another relation.
     * @return the resulting relation.
     */
    Relation union(Relation right);

    /**
     * Intersect this relation with `right` one.
     *
     * @param right another relation.
     * @return the resulting relation.
     */
    Relation intersect(Relation right);

    /**
     * Minus this relation with `right` one.
     *
     * @param right another relation.
     * @return the resulting relation.
     */
    Relation minus(Relation right);

    /**
     * Filters tuples of this relation based on the predicate.
     *
     * @pre predicate attribute names must belong to attributes of the relation.
     * @param predicate a predicate to use for filtering.
     * @return the resulting relation.
     */
    Relation restrict(Predicate predicate);

    <R> R accept(Visitor<R> visitor);

    /**
     * Returns a stream over this relation's tuples.
     *
     * @return a stream of tuples.
     */
    Stream<Tuple> stream();

    /**
     * Returns the cardinality of this relation, as its number of tuples.
     *
     * This method is a terminal operation. Note that its actual complexity/cost
     * depends on the kind of relation you are facing. For instance, an entire
     * stream of tuples may be consumed for the size of a complex relational
     * expression to be computed in practice.
     *
     * @return the cardinality of the relation, i.e. the number of tuples.
     */
    long cardinality();

}
