package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.relation.algebra.Intersect;

public class OptimizedIntersect extends Optimized<Intersect> {

    public OptimizedIntersect(Optimizer optimizer, Intersect operator) {
        super(optimizer, operator);
    }

    /**
     * project(intersect(l,r),a) => intersect(project(l,a),project(r,a)).
     */
    @Override
    public Relation project(AttrList attributes){
        // get the first operand
        Relation left = operator.getLeft();
        left = optimized(left).project(attributes);

        // get the second operand
        Relation right = operator.getRight();
        right = optimized(right).project(attributes);

        Relation r = left.intersect(right);

        return r;
    }

    /**
     * restrict(intersect(l,r),p) => intersect(restrict(l,p),restrict(r,p))
     */
    @Override
    public Relation restrict(Predicate predicate) {
        // get the first operand
        Relation left = operator.getLeft();
        left = optimized(left).restrict(predicate);

        // get the second operand
        Relation right = operator.getRight();
        right = optimized(right).restrict(predicate);

        Relation r = left.intersect(right);

        return r;
    }

}