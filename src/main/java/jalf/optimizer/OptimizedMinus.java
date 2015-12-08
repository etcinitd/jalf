package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.relation.algebra.Minus;

public class OptimizedMinus extends Optimized<Minus>{

    public OptimizedMinus(Optimizer optimizer, Minus operator) {
        super(optimizer, operator);
    }

    /**
     * project(minus(l,r),a) => minus(project(l,a),project(r,a)).
     */
    @Override
    public Relation project(AttrList attributes){
        // get the first operand
        Relation left = operator.getLeft();
        left = optimized(left).project(attributes);

        // get the second operand
        Relation right = operator.getRight();
        right = optimized(right).project(attributes);

        Relation r = left.minus(right);

        return r;
    }

    /**
     * restrict(minus(l,r),p) => minus(restrict(l,p),restrict(r,p))
     */
    @Override
    public Relation restrict(Predicate predicate) {
        // get the first operand
        Relation left = operator.getLeft();
        left = optimized(left).restrict(predicate);

        // get the second operand
        Relation right = operator.getRight();
        right = optimized(right).restrict(predicate);

        Relation r = left.minus(right);

        return r;
    }
}
