package jalf.relation.algebra;

import jalf.*;
import jalf.compiler.Cog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Relational projection.
 */
public class Project extends UnaryOperator {

    private final Relation operand;
    private final AttrList attributes;
    private final Heading heading;

    public Project(Relation operand, AttrList attributes) {
        this.operand = operand;
        this.attributes = attributes;
        this.heading = constructHeading();
    }

    public Relation getOperand() {
        return operand;
    }

    public AttrList getAttributes() {
        return attributes;
    }

    @Override
    public Heading heading() {
        return heading;
    }

    private Heading constructHeading() {
        Map<AttrName, AttrType> newAttrTypes = new ConcurrentHashMap<>();
        operand.heading().getAttrTypes().forEach((name, type) -> {
            if (attributes.contains(name))
                newAttrTypes.put(name, type);
        });
        return Heading.headingOf(newAttrTypes);
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled.project(this, compiled);
    }
}
