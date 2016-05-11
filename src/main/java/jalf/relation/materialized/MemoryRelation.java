package jalf.relation.materialized;

import java.util.stream.Collector;

import jalf.Relation;
import jalf.Tuple;
import jalf.compiler.AbstractRelation;
import jalf.constraint.Keys;
import jalf.relation.algebra.LeafOperand;
import jalf.type.RelationType;

/**
 * Parent (abstract) class of all relations captured as in-memory tuple sets,
 * whatever their actual representation.
 */
public abstract class MemoryRelation extends AbstractRelation implements LeafOperand {

    protected Keys keys;

    @Override
    public Keys getKeys() {
        return keys;
    }

    public static Collector<Tuple, ?, Relation> collector(RelationType type) {
        return SetMemoryRelation.collector(type);
    }

    public static Collector<Tuple, ?, Relation> collector() {
        return SetMemoryRelation.collector();
    }

}
