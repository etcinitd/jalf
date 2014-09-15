package jalf.relation.algebra;

import jalf.Relation;
import jalf.compiler.AbstractRelation;
import jalf.compiler.Cog;

/**
 * Specialization of Operator for operators with only one relational operand.
 */
public abstract class UnaryOperator extends Operator {

    public abstract Relation getOperand();

    public Cog compile() {
        AbstractRelation op = (AbstractRelation) getOperand();
        return compile(op.compile());
    }

    protected abstract Cog compile(Cog compiled);

}
