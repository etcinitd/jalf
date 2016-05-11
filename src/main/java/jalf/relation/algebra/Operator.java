package jalf.relation.algebra;

import jalf.Relation;
import jalf.compiler.AbstractRelation;
import jalf.constraint.Keys;
import jalf.relation.materialized.MemoryRelation;
import jalf.type.RelationType;

import java.util.List;

/**
 * Parent (abstract) class for all relations captured by lazy-evaluated
 * algebraic expressions.
 */
public abstract class Operator extends AbstractRelation {

    protected Keys keys;

    protected abstract Keys lazyComputeKey();

    protected abstract RelationType typeCheck();

    public abstract List<Relation> getOperands();

    public abstract List<Object> getArguments();

    @Override
    public Keys getKeys() {
        if (this.keys == null) {
            this.keys = this.lazyComputeKey();
        }
        return this.keys;
    }

    @Override
    public boolean equals(Relation other) {
        if (other instanceof MemoryRelation)
            return other.equals(this);
        else
            return stream()
                    .collect(MemoryRelation.collector(getType()))
                    .equals(other);
    }

}
