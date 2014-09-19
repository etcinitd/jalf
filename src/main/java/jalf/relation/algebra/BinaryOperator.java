package jalf.relation.algebra;

import jalf.Relation;
import jalf.compiler.AbstractRelation;
import jalf.compiler.Cog;

/**
 * Specialization of Operator for operators with two relational operands.
 */
public abstract class BinaryOperator extends Operator {

    public abstract Relation getLeft();
    public abstract Relation getRight();

    public Cog compile() {
        AbstractRelation left = (AbstractRelation) getLeft();
        AbstractRelation right = (AbstractRelation) getRight();
        return compile(left.compile(), right.compile());
    }

    protected abstract Cog compile(Cog left, Cog right);

}
