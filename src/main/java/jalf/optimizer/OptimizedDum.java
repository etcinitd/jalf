package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.relation.algebra.Dum;

public class OptimizedDum extends Optimized<Dum> {

    public OptimizedDum(Optimizer optimizer, Dum operator) {
        super(optimizer, operator);
    }

    /**
     * project(DUM, attrs) -> DUM
     *
     * As DUM has no attribute, this method should never be called (but with
     * an empty attribute list) if type-checking works properly. However, it is
     * always safe to return DUM itself.
     */
    @Override
    public Relation project(AttrList attributes) {
        return operator;
    }

    /**
     * rename(DUM, r) -> DUM
     *
     * As DUM has no attribute, this method should never be called (but with
     * an empty renaming) if type-checking works properly. However, it is
     * always safe to return DUM itself.
     */
    @Override
    public Relation rename(Renaming renaming) {
        return operator;
    }

    /**
     * restrict(DUM, r) -> DUM
     *
     * As DUM has no tuple, it should not be restricted further. It is
     * therefore safe to always return DUM itself.
     */
    @Override
    public Relation restrict(Predicate predicate) {
        return operator;
    }

    /**
     * join(DUM, other) -> DUM
     *
     * As DUM has no tuple, joining with another might not generate any. It
     * is therefore safe to always return DUM itself.
     */
    @Override
    public Relation join(Relation right) {
        return operator;
    }

}
