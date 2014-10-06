package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.relation.algebra.Project;

/**
 * Decorates an optimized projection.
 * 
 * This class implements the following optimizations:
 *
 * - project(project(x))  => project(x)
 * - restrict(project(x)) => project(restrict(x))
 */
public class OptimizedProject extends Optimized<Project> {

    public OptimizedProject(Optimizer optimizer, Project operator) {
        super(optimizer, operator);
    }

    /**
     * project(project(r, ...), a) => project(r, a)
     */
    @Override
    public Relation project(AttrList attributes) {
        Relation r = operator.getOperand();
        r = optimized(r).project(attributes);
        return r;
    }

    /**
     * restrict(project(r, a), p) => project(restrict(r, p), a)
     */
    @Override
    public Relation restrict(Predicate pred) {
        Relation r = operator.getOperand();
        r = optimized(r).restrict(pred);
        r = optimized(r).project(operator.getAttributes());
        return r;
    }

}
