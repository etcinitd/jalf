package jalf.relation.algebra;

import jalf.Relation;
import jalf.Tuple;
import jalf.Visitor;
import jalf.compiler.AbstractRelation;
import jalf.compiler.Cog;
import jalf.type.RelationType;

import java.util.Collections;

/**
 * Dee, the relation with no attribute and no tuple.
 */
public class Dum extends AbstractRelation {

    private static final RelationType TYPE = RelationType.varargs();

    private static Relation INSTANCE;

    private Dum() {}

    public static synchronized Relation instance() {
        if (INSTANCE == null) {
            INSTANCE = new Dum();
        }
        return INSTANCE;
    }

    @Override
    public RelationType getType() {
        return TYPE;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    public Cog toCog() {
        return new Cog(this, () -> Collections.<Tuple>emptyList().stream());
    }

    @Override
    public boolean equals(Relation arg) {
        if (arg == this)
            return true;
        if (arg == null || !(arg instanceof Relation))
            return false;
        Relation other = (Relation) arg;
        return TYPE.equals(other.getType()) &&
                !other.stream().findFirst().isPresent();
    }

}
