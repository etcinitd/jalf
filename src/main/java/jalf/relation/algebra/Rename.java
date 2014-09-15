package jalf.relation.algebra;

import jalf.Relation;
import jalf.Renaming;

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

}
