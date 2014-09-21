package jalf.relation.algebra;

import jalf.Relation;
import jalf.Renaming;
import jalf.compiler.Cog;
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
    protected Cog compile(Cog compiled) {
        return compiled.rename(this, compiled);
    }
}
