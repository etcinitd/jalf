package jalf.relation.algebra;

import jalf.*;
import jalf.compiler.Cog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Relational renaming.
 */
public class Rename extends UnaryOperator {

    private final Relation operand;
    private final Renaming renaming;
    private final Heading heading;

    public Rename(Relation operand, Renaming renaming) {
        this.operand = operand;
        this.renaming = renaming;
        this.heading = constructHeading();
    }

    public Relation getOperand() {
        return operand;
    }

    public Renaming getRenaming() {
        return renaming;
    }

    @Override
    public Heading heading() {
        return heading;
    }

    private Heading constructHeading() {
        Map<AttrName, AttrType> newAttrTypes = new ConcurrentHashMap<>();
        operand.heading().getAttrTypes().forEach((name, type) -> newAttrTypes.put(renaming.apply(name), type));
        return Heading.headingOf(newAttrTypes);
    }

    @Override
    protected Cog compile(Cog compiled) {
        return compiled.rename(this, compiled);
    }
}
