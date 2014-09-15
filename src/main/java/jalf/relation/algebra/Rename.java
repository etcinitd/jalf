package jalf.relation.algebra;

import jalf.Relation;
import jalf.Renaming;
import jalf.compiler.Cog;

/**
 * Relational renaming.
 */
public class Rename extends UnaryOperator {

    private Relation operand;

    private Renaming renaming;

    public Rename(Relation operand, Renaming renaming) {
        this.operand = operand;
        this.renaming = renaming;
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
