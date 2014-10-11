package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.Selection;
import jalf.relation.algebra.Select;

public class OptimizedSelect extends Optimized<Select> {

    public OptimizedSelect(Optimizer optimizer, Select operator) {
        super(optimizer, operator);
    }

    /**
     * project(select(r,s),p) => select(r,s|p)
     * where
     *   - s|p is the projection of s on p
     */
    @Override
    public Relation project(AttrList p) {
        Relation  r = operator.getOperand();
        Selection s = operator.getSelection();
        r = optimized(r).select(s.project(p));
        return r;
    }

    /**
     * rename(select(r,s),a) => select(r,s|a)
     * where
     *   - s|a is s when attributes have been renamed according to a
     */
    @Override
    public Relation rename(Renaming a) {
        Relation  r = operator.getOperand();
        Selection s = operator.getSelection();
        r = optimized(r).select(s.rename(a));
        return r;
    }

    @Override
    public Relation restrict(Predicate predicate) {
        // TODO Auto-generated method stub
        return super.restrict(predicate);
    }

}
