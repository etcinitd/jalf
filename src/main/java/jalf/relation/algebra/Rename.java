package jalf.relation.algebra;

import jalf.Relation;
import jalf.Renaming;
import jalf.Visitor;
import jalf.type.RelationType;

/**
 * Relational renaming.
 */
public class Rename extends UnaryOperator {

    private final Relation operand;

    private final Renaming renaming;

    private RelationType type;

    public Rename(Relation operand, Renaming renaming, RelationType type) {
        this.operand = operand;
        this.renaming = renaming;
        this.type = type;
    }

    public Rename(Relation operand, Renaming renaming) {
        this.operand = operand;
        this.renaming = renaming;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        // TODO: implement proper type checking (?)
        return operand.getType().rename(renaming);
    }

    public Relation getOperand() {
        return operand;
    }

    public Renaming getRenaming() {
        return renaming;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

}
