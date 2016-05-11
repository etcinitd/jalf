package jalf.optimizer;

import jalf.Relation;
import jalf.Visitor;
import jalf.relation.algebra.Dee;
import jalf.relation.algebra.Dum;
import jalf.relation.algebra.Intersect;
import jalf.relation.algebra.Join;
import jalf.relation.algebra.LeafOperand;
import jalf.relation.algebra.Minus;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;
import jalf.relation.algebra.Select;
import jalf.relation.algebra.Summarize;
import jalf.relation.algebra.Union;

/**
 * This class decorates relational operators as their optimized counterparts.
 * It implements `Visitor` as an efficient way of applying per-operator
 * delegation but is not a visitor per-se, as it does not properly visit the
 * AST.
 */
public class DefaultMapper implements Visitor<Optimized<?>> {

    private Optimizer optimizer;

    public DefaultMapper(Optimizer optimizer) {
        this.optimizer = optimizer;
    }

    @Override
    public Optimized<?> apply(Relation relation) {
        return relation.accept(this);
    }

    ///

    @Override
    public Optimized<?> visit(Select relation) {
        return new OptimizedSelect(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Project relation) {
        return new OptimizedProject(optimizer, relation);
    }

    @Override
    public Optimized<?>  visit(Summarize relation) {
        return new OptimizedSummarize(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Rename relation) {
        return new OptimizedRename(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Restrict relation) {
        return new OptimizedRestrict(optimizer, relation);
    }

    ///

    @Override
    public Optimized<?> visit(Join relation) {
        return new OptimizedJoin(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Union relation) {
        return new OptimizedUnion(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Intersect relation) {
        return new OptimizedIntersect(optimizer, relation);
    }

    ///

    @Override
    public Optimized<?> visit(LeafOperand relation) {
        return new Optimized<Relation>(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Dee relation) {
        return new OptimizedDee(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Dum relation) {
        return new OptimizedDum(optimizer, relation);
    }

    @Override
    public Optimized<?> visit(Minus relation) {
        return new OptimizedMinus(optimizer, relation);
    }

}
