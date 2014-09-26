package jalf.relation.algebra;

import jalf.Predicate;
import jalf.Relation;
import jalf.compiler.Cog;
import jalf.type.RelationType;

/**
 * Relational restrict.
 */
public class Restrict extends UnaryOperator {

    private final Relation operand;

    private final Predicate predicate;

    private RelationType type;

    public Restrict(Relation operand, Predicate predicate, RelationType type) {
        this.operand = operand;
        this.predicate = predicate;
        this.type = type;
    }

    public Restrict(Relation operand, Predicate predicate) {
        this.operand = operand;
        this.predicate = predicate;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().restrict(predicate);
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
