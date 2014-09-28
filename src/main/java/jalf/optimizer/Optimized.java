package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Visitor;
import jalf.compiler.AbstractRelation;
import jalf.type.RelationType;

/**
 * Decorates a relation operator <R> that has been optimized and expose the
 * algebra as optimization methods.
 *
 * This class implements the identity decorator, that is, its algebra methods
 * perform no optimization but a simple AST cloning. The class is intended to
 * be subclassed per-operator.
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
        return operator.project(attributes);
    }

    @Override
    public Relation rename(Renaming renaming) {
        return operator.rename(renaming);
    }

    @Override
    public Relation restrict(Predicate predicate) {
        return operator.restrict(predicate);
    }

    @Override
    public Relation join(Relation right) {
        return operator.join(right);
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
