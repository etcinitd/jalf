package jalf.relation.algebra;

import jalf.AttrList;
import jalf.Relation;
import jalf.compiler.Cog;
import jalf.type.RelationType;

/**
 * Relational projection.
 */
public class Project extends UnaryOperator {

    private Relation operand;

    private AttrList attributes;

    private RelationType type;

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
    }

    @Override
    public RelationType getType() {
        if (type == null) {
            type = operand.getType().project(attributes);
        }
        return type;
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
