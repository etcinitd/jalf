package jalf.optimizer;

import jalf.Relation;
import jalf.Visitor;
import jalf.relation.algebra.Dee;
import jalf.relation.algebra.Dum;
import jalf.relation.algebra.Join;
import jalf.relation.algebra.LeafOperand;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;

/**
 * Logical optimizer of relational expressions.
 *
 * Logical optimization is a process that rewrites relational expressions as
 * equivalent ones, but presenting a profile that leads to a better execution
 * plan when compiled. Typical optimizations include pushing restrictions and
 * projections as deep as possible in the tree.
 *
 * This visitor provides logical optimization and works in a similar way to the
 * compiler itself (from an architectural point of view at least). That is, it
 * optimizes using a depth-first visit of the AST. Leaf operands are optimized
 * as themselves. Result of optimizations are successively decorated as
 * `Optimized` and its subclasses (per operator) and the optimizations delegated
 * to those by pure AST rewriting. The optimization strategies are thus
 * implemented in the various algebra methods of Optimized and its subclasses.
 */
public class Optimizer implements Visitor<Relation> {

    private Decorator mapper;

    public Optimizer() {
        this.mapper = new Decorator(this);
    }

    protected Relation optimized(Relation relation) {
        return mapper.decorate(relation);
    }

    ///

    public Relation visit(Project relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).project(relation.getAttributes());
    }

    public Relation visit(Rename relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).rename(relation.getRenaming());
    }

    public Relation visit(Restrict relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).restrict(relation.getPredicate());
    }

    ///

    public Relation visit(Join relation) {
        Relation left = relation.getLeft().accept(this);
        Relation right = relation.getRight().accept(this);
        return optimized(left).join(right);
    }

    ///

    public Relation visit(LeafOperand relation) {
        return relation;
    }

    @Override
    public Relation visit(Dee relation) {
        return relation;
    }

    @Override
    public Relation visit(Dum relation) {
        return relation;
    }

}
