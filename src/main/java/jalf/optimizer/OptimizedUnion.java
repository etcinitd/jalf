package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.relation.algebra.Union;

public class OptimizedUnion  extends Optimized<Union>{

    public OptimizedUnion(Optimizer optimizer, Union operator) {
        super(optimizer, operator);
    }

    /**
     * project(union(l,r),a) => union(project(l,a),project(r,a).
     */
    @Override
    public Relation project(AttrList attributes){
        // get the first operand
        Relation left = operator.getLeft();
        left = optimized(left).project(attributes);

        // get the second operand
        Relation right = operator.getRight();
        right = optimized(right).project(attributes);

        Relation r = left.union(right);

        return r;
    }

    /**
     * restrict(union(l,r),p) => union(restrict(l,p),restrict(r,p))
     */
    @Override
    public Relation restrict(Predicate predicate) {
        // get the first operand
        Relation left = operator.getLeft();
        left = optimized(left).restrict(predicate);

        // get the second operand
        Relation right = operator.getRight();
        right = optimized(right).restrict(predicate);

        Relation r = left.union(right);

        return r;
    }

}
