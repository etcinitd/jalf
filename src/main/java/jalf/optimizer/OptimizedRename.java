package jalf.optimizer;

import jalf.AttrList;
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
//    @Override
//    public Relation project(AttrList attributes) {
//        Renaming renaming = operator.getRenaming();
//        if (!renaming.isReversible()) {
//            return super.project(attributes);
//        }
//
//        // Find the names of projection attributes on r itself by renaming them
//        // the other way round.
//        AttrList rAttributes = attributes.rename(renaming.reverse());
//
//        // Renaming to apply on outer operator is the same as previous, but
//        // the attributes projected away
//        Renaming outRenaming = renaming.project(attributes);
//
//        // let's go now!
//        Relation r = operator.getOperand();
//        r = optimized(r).project(rAttributes);
//        r = optimized(r).rename(outRenaming);
//        return r;
//    }

}
