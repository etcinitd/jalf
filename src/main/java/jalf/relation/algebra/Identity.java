package jalf.relation.algebra;

import jalf.Relation;
import jalf.compiler.Cog;

public class Identity extends UnaryOperator {

    private Relation operand;

    public Identity(Relation operand) {
        this.operand = operand;
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled;
    }

}
