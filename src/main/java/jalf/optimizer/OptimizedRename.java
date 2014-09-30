package jalf.optimizer;

import jalf.AttrList;
import jalf.Predicate;
import jalf.Relation;
import jalf.Renaming;
import jalf.relation.algebra.Rename;

public class OptimizedRename extends Optimized<Rename> {

    public OptimizedRename(Optimizer optimizer, Rename operator) {
        super(optimizer, operator);
    }

    /**
     * project(rename(r, n), a) => rename(project(r, a|n), n|a)
     * where
     *   - a|n is the projection attributes renamed the inversed way
     *   - n|a is the projection of `n` renaming on attributes `a` only
     */
    @Override
    public Relation project(AttrList attributes) {
        Renaming renaming = operator.getRenaming();
        if (!renaming.isReversible()) {
            return super.project(attributes);
        }

        // Find the names of projection attributes on r itself by renaming them
        // the other way round.
        AttrList rAttributes = attributes.rename(renaming.reverse());

        // Renaming to apply on outer operator is the same as previous, but
        // the attributes projected away
        Renaming outRenaming = renaming.project(rAttributes);

        // let's go now!
        Relation r = operator.getOperand();
        r = optimized(r).project(rAttributes);
        r = optimized(r).rename(outRenaming);
        return r;
    }

    /**
     * restrict(rename(r, n), p) => rename(restrict(r, p|n), n)
     * where
     *   - p|n is the predicate where attribute references have been renamed
     *     the other way round.
     */
    @Override
    public Relation restrict(Predicate predicate) {
        if (!predicate.isStaticallyAnalyzable()) {
            return super.restrict(predicate);
        }

        // rename the predicate with the inversed renaming
        Renaming renaming = operator.getRenaming();
        Renaming rRenaming = renaming.reverse();
        Predicate rPredicate = predicate.rename(rRenaming);

        Relation r = operator.getOperand();
        r = optimized(r).restrict(rPredicate);
        r = optimized(r).rename(renaming);
        return r;
    }

}
