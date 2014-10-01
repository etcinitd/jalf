package jalf.optimizer;

import jalf.AttrList;
import jalf.Relation;
import jalf.relation.algebra.Join;

public class OptimizedJoin extends Optimized<Join> {

    public OptimizedJoin(Optimizer optimizer, Join operator) {
        super(optimizer, operator);
    }

    /**
     * project(join(l,r),a) => project(join(project(l,(a+j)&l),project(r,(a+j)&r)),a)
     * where
     *   - (a+j)&l is the projection attributes plus the attributes needed for
     *     the join itself (that might be otherwise projected away) but only
     *     those that are part of left's heading.
     *   - (a+j)&r is similar for the right operand.
     *
     * Now, some of the three projections may appear useless (i.e. they do not
     * project anything away) and are properly removed accordingly.
     */
    @Override
    public Relation project(AttrList attributes) {
        // First, compute a superset of `attributes` that also takes join
        // attributes into account
        AttrList joinAttrs = operator.getJoinAttrList();
        AttrList withJoinAttrs = attributes.union(joinAttrs);

        // now project each operand in turn on those attributes
        Relation left = projectOperandOn(operator.getLeft(), withJoinAttrs);
        Relation right = projectOperandOn(operator.getRight(), withJoinAttrs);

        // recompute that join first
        Relation r = optimized(left).join(right);

        // if not equal it means that extra join attributes have been added by
        // the union above. We need to project them away now.
        if (!withJoinAttrs.equals(attributes)) {
            r = r.project(attributes);
        }
        return r;
    }

    /**
     * Project an operand on some attributes. `attributes` may be a superset
     * of the operand's ones. This method takes care of projecting on the
     * common set of attributes. Also, this method makes sure to only project
     * the operand if it's actually needed.
     */
    private Relation projectOperandOn(Relation operand, AttrList attributes) {
        // `attributes` may be a superset of operand's ones. Take the
        // intersection. `attrs` will be a (non-proper) subset thus.
        AttrList opAttrs = operand.getType().toAttrList();
        AttrList attrs = opAttrs.intersect(attributes);

        // if attrs is equals, it is not a subset and the projection is no
        // longer needed
        if (!attrs.equals(opAttrs)) {
            operand = optimized(operand).project(attrs);
        }
        return operand;
    }
    
}
