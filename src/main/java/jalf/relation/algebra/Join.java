package jalf.relation.algebra;

import jalf.AttrName;
import jalf.AttrType;
import jalf.Heading;
import jalf.Relation;
import jalf.compiler.Cog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Relational natural join based on same name attributes.
 */
public class Join extends BinaryOperator {

    private final Relation left;
    private final Relation right;
    private final Heading heading;

    public Join(Relation left, Relation right) {
        this.left = left;
        this.right = right;
        this.heading = constructHeading();
    }

    @Override
    public Relation getLeft() {
        return left;
    }

    @Override
    public Relation getRight() {
        return right;
    }

    @Override
    public Heading heading() {
        return heading;
    }

    private Heading constructHeading() {
        Map<AttrName, AttrType> joinedAttrTypes = new ConcurrentHashMap<>();
        joinedAttrTypes.putAll(left.heading().getAttrTypes());
        joinedAttrTypes.putAll(right.heading().getAttrTypes());
        return Heading.headingOf(joinedAttrTypes);
    }

    @Override
    protected Cog compile(Cog left, Cog right) {
        return left.join(this, left, right);
    }
}
