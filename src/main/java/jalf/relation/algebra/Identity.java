package jalf.relation.algebra;

import jalf.Heading;
import jalf.Relation;
import jalf.compiler.Cog;

public class Identity extends UnaryOperator {

    private final Relation operand;

    public Identity(Relation operand) {
        this.operand = operand;
    }

    @Override
    public Relation getOperand() {
        return operand;
    }

    @Override
    public Heading heading() {
        return operand.heading();
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled;
    }

}
