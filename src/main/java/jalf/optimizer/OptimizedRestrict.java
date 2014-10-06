package jalf.optimizer;

import jalf.Predicate;
import jalf.Relation;
import jalf.relation.algebra.Restrict;

public class OptimizedRestrict extends Optimized<Restrict> {

    public OptimizedRestrict(Optimizer optimizer, Restrict operator) {
        super(optimizer, operator);
    }

    /**
     * restrict(restrict(r, i), o) => restrict(r, i & o)
     */
    @Override
    public Relation restrict(Predicate outer) {
        Predicate inner = operator.getPredicate();
        Relation r = operator.getOperand();
        r = optimized(r).restrict(inner.and(outer));
        return r;
    }

}
