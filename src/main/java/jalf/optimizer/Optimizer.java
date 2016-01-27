package jalf.optimizer;

import jalf.AttrName;
import jalf.Relation;
import jalf.Visitor;
import jalf.aggregator.Aggregator;
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

import java.util.function.Function;

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

    private Function<Relation,Optimized<?>> mapper;

    public Optimizer(Function<Relation,Optimized<?>> mapper) {
        this.mapper = mapper;
    }

    public Optimizer() {
        this.mapper = new DefaultMapper(this);
    }

    protected Relation optimized(Relation relation) {
        return mapper.apply(relation);
    }

    ///

    @Override
    public Relation visit(Select relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).select(relation.getSelection());
    }

    @Override
    public Relation visit(Project relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).project(relation.getAttributes());
    }

    @Override
    public Relation visit(Summarize relation) {
        Relation optimized = relation.getOperand().accept(this);
        Aggregator <?> aggregator = relation.getAggregator();
        AttrName as =relation.getAs();
        return optimized(optimized).summarize(relation.getBy(),aggregator,as);
    }

    @Override
    public Relation visit(Rename relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).rename(relation.getRenaming());
    }

    @Override
    public Relation visit(Restrict relation) {
        Relation optimized = relation.getOperand().accept(this);
        return optimized(optimized).restrict(relation.getPredicate());
    }

    ///

    @Override
    public Relation visit(Join relation) {
        Relation left = relation.getLeft().accept(this);
        Relation right = relation.getRight().accept(this);
        return optimized(left).join(right);
    }

    @Override
    public Relation visit(Union relation) {
        Relation left = relation.getLeft().accept(this);
        Relation right = relation.getRight().accept(this);
        return optimized(left).union(right);
    }

    @Override
    public Relation visit(Intersect relation) {
        Relation left = relation.getLeft().accept(this);
        Relation right = relation.getRight().accept(this);
        return optimized(left).intersect(right);
    }

    @Override
    public Relation visit(Minus relation) {
        Relation left = relation.getLeft().accept(this);
        Relation right = relation.getRight().accept(this);
        return optimized(left).minus(right);
    }

    ///

    @Override
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
