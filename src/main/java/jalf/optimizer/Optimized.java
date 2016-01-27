package jalf.optimizer;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
import jalf.compiler.AbstractRelation;
import jalf.relation.materialized.EmptyRelation;
import jalf.type.RelationType;

/**
 * Decorates a relation operator <R> that has been optimized and exposes the
 * algebra as optimization methods.
 *
 * This class implements basic optimization strategies shared by all operators
 * and operands (such as removing unnecessary projections). It is intended to
 * be subclassed per-operator to provide smarter strategies.
 *
 * @param <R> the type of the optimized operator.
 */
public class Optimized<R extends Relation> extends AbstractRelation {

    protected Optimizer optimizer;

    protected R operator;

    public Optimized(Optimizer optimizer, R operator) {
        this.optimizer = optimizer;
        this.operator = operator;
    }

    ///

    protected Relation optimized(Relation r) {
        return optimizer.optimized(r);
    }

    ///

    @Override
    public RelationType getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V> V accept(Visitor<V> visitor) {
        throw new UnsupportedOperationException();
    }

    ///

    @Override
    public Relation project(AttrList attributes) {
        AttrList opAttrs = operator.getType().toAttrList();

        // the projection does not project anything away? just bypass it.
        if (opAttrs.equals(attributes)) {
            return operator;
        }

        return operator.project(attributes);
    }

    @Override
    public Relation summarize(AttrList by, Aggregator<?> aggregat, AttrName as) {
        return operator.summarize(by, aggregat, as);
    }

    @Override
    public Relation rename(Renaming renaming) {
        return operator.rename(renaming);
    }

    @Override
    public Relation restrict(Predicate predicate) {
        // restrict(r, TRUE) -> r
        if (predicate == Predicate.TRUE)
            return operator;

        // restrict(r, FALSE) -> empty relation, same type of r
        if (predicate == Predicate.FALSE)
            return EmptyRelation.factor(operator.getType());

        return operator.restrict(predicate);
    }

    @Override
    public Relation join(Relation right) {
        // join(r, DUM) -> DUM
        if (right == Relation.DUM)
            return right;

        // join(r, DEE) -> r
        if (right == Relation.DEE)
            return operator;

        return operator.join(right);
    }

    @Override
    public Relation union(Relation right){
        return operator.union(right);
    }

    @Override
    public Relation intersect(Relation right){
        return operator.intersect(right);
    }

    @Override
    public Relation minus(Relation right){
        return operator.minus(right);
    }

    ///

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Relation other) {
        throw new UnsupportedOperationException();
    }

    public boolean sameExpressionAs(Relation other) {
        throw new UnsupportedOperationException();
    }

}