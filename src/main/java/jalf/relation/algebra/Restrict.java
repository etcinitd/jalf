package jalf.relation.algebra;

import jalf.Heading;
import jalf.Predicate;
import jalf.Relation;
import jalf.compiler.Cog;

/**
 * Relational restrict.
 */
public class Restrict extends UnaryOperator {

    private final Relation operand;
    private final Predicate predicate;

    public Restrict(Relation operand, Predicate predicate) {
        this.operand = operand;
        this.predicate = predicate;
    }

    public Relation getOperand() {
        return operand;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public Heading heading() {
        return operand.heading();
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled.restrict(this, compiled);
    }

}
