package jalf.relation.algebra;

import jalf.Relation;
import jalf.compiler.AbstractRelation;
import jalf.relation.materialized.MemoryRelation;

/**
 * Parent (abstract) class for all relations captured by lazy-evaluated
 * algebraic expressions.
 */
public abstract class Operator extends AbstractRelation {

    public boolean equals(Relation other) {
        if (other instanceof MemoryRelation)
            return other.equals(this);
        else
            return stream()
                .collect(MemoryRelation.collector(getType()))
                .equals(other);
    }

}
