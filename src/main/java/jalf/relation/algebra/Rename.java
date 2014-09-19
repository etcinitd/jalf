package jalf.relation.algebra;

import jalf.Relation;
import jalf.Renaming;
import jalf.compiler.Cog;
import jalf.type.RelationType;

/**
 * Relational renaming.
 */
public class Rename extends UnaryOperator {

    private Relation operand;

    private Renaming renaming;

    private RelationType type;

    public Rename(Relation operand, Renaming renaming) {
        this.operand = operand;
        this.renaming = renaming;
    }

    @Override
    public RelationType getType() {
        if (type == null) {
            type = operand.getType().rename(renaming);
        }
        return type;
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
