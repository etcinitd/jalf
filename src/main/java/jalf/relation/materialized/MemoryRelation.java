package jalf.relation.materialized;

import jalf.Relation;
import jalf.Tuple;
import jalf.compiler.AbstractRelation;

import java.util.stream.Collector;

/**
 * Parent (abstract) class of all relations captured as in-memory tuple sets,
 * whatever their actual representation.
 */
public abstract class MemoryRelation extends AbstractRelation {

    public static Collector<Tuple, ?, Relation> collector() {
        return SetMemoryRelation.collector();
    }

}
