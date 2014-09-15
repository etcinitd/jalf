package jalf.relation.algebra;

import jalf.AttrList;
import jalf.Relation;

/**
 * Relational projection.
 */
public class Project extends UnaryOperator {

    private Relation operand;

    private AttrList attributes;

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
    }

    public Relation getOperand() {
        return operand;
    }

    public AttrList getAttributes() {
        return attributes;
    }

}
