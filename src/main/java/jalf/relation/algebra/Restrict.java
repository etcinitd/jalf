package jalf.relation.algebra;

import jalf.Predicate;
import jalf.Relation;
import jalf.compiler.Cog;
import jalf.type.RelationType;

/**
 * Relational restrict.
 */
public class Restrict extends UnaryOperator {

    private Relation operand;

    private Predicate predicate;

    private RelationType type;

    public Restrict(Relation operand, Predicate predicate) {
        this.operand = operand;
        this.predicate = predicate;
    }

    @Override
    public RelationType getType() {
        if (type == null) {
            type = operand.getType().restrict(predicate);
        }
        return type;
    }

    public Relation getOperand() {
        return operand;
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled.restrict(this, compiled);
    }

}
