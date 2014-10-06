package jalf.relation.materialized;

import java.util.Collections;

import jalf.Relation;
import jalf.Tuple;
import jalf.Visitor;
import jalf.compiler.Cog;
import jalf.compiler.Compiler;
import jalf.type.RelationType;

public class EmptyRelation extends MemoryRelation {

    private RelationType type;

    private EmptyRelation(RelationType type) {
        super();
        this.type = type;
    }

    public static Relation factor(RelationType type) {
        return new EmptyRelation(type);
    }

    @Override
    public RelationType getType() {
        return type;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Cog toCog(Compiler compiler) {
        return new Cog(this, () -> Collections.<Tuple>emptyList().stream());
    }

    @Override
    public boolean equals(Relation other) {
        return !other.stream().findAny().isPresent();
    }

}