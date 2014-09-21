package jalf.relation.algebra;

import jalf.*;
import jalf.compiler.Cog;
import jalf.type.RelationType;

/**
 * Relational projection.
 */
public class Project extends UnaryOperator {

    private final Relation operand;

    private final AttrList attributes;

    private final RelationType type;

    public Project(Relation operand, AttrList attributes, RelationType type) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = type;
    }

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
        this.type = typeCheck();
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    protected RelationType typeCheck() {
        return operand.getType().project(attributes);
    }

    public Relation getOperand() {
        return operand;
    }

    public AttrList getAttributes() {
        return attributes;
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled.project(this, compiled);
    }
}
