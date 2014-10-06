package jalf.optimizer;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.relation.algebra.Dee;

public class OptimizedDee extends Optimized<Dee> {

    public OptimizedDee(Optimizer optimizer, Dee operator) {
        super(optimizer, operator);
    }

    /**
     * project(DEE, attrs) -> DEE
     *
     * As DEE has no attribute, this method should never be called (but with
     * an empty attribute list) if type-checking works properly. However, it is
     * always safe to return DEE itself.
     */
    @Override
    public Relation project(AttrList attributes) {
        return operator;
    }

    /**
     * rename(DEE, r) -> DEE
     *
     * As DEE has no attribute, this method should never be called (but with
     * an empty renaming) if type-checking works properly. However, it is
     * always safe to return DEE itself.
     */
    @Override
    public Relation rename(Renaming renaming) {
        return operator;
    }

    /**
     * join(DEE, r) -> r
     *
     * As DEE has one tuple with no attributes, that tuple naturally joins with
     * every tuple from right, exactly once for each. That leads to right
     * itself of course.
     */
    @Override
    public Relation join(Relation right) {
        return right;
    }

}
