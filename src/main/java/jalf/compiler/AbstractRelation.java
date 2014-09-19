package jalf.compiler;

import jalf.*;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;
import jalf.type.TupleType;

import java.util.stream.Stream;

/**
 * Parent (abstract) class of all relation implementations.
 *
 * Abstract relations are able to compile themselves as Cogs that encapsulate
 * a stream of tuples, which is the one served by `stream()`. The actual
 * compilation process depends on the kind of Relation (e.g. expressions
 * vs. in-memory relations). See documentation of those for more about the
 * compilation process.
 */
public abstract class AbstractRelation implements Relation {

    private TupleType tupleType;

    @Override
    public TupleType getTupleType() {
        if (tupleType == null) {
            tupleType = getType().toTupleType();
        }
        return tupleType;
    }

    ///

    @Override
    public Relation project(AttrList attributes) {
        return new Project(this, attributes);
    }

    @Override
    public Relation rename(Renaming renaming) {
        return new Rename(this, renaming);
    }

    @Override
    public Relation restrict(Predicate predicate) {
        return new Restrict(this, predicate);
    }

    ///

    @Override
    public Stream<Tuple> stream() {
        return compile().stream();
    }

    @Override
    public long count() {
        return stream().count();
    }

    ///

    public abstract Cog compile();

    ///

    /**
     * As all Relation(s) denote values (even if some of them are lazy evaluated)
     * we want equals() to apply to those values independently of their actual
     * representation. This commit implements this on the basic infrastructure,
     * making the acceptance test pass for now.
     *
     * The following logic is currently implemented:
     *
     * 1. AbstractRelation checks trivial cases then delegates to the abstract
     *    `equals(Relation)` method.
     * 2. SetMemoryRelation implements two strategies:
     *    2.1 against another set-based (through underlying collection equality)
     *    2.2 against another relation (through memory reload).
     * 3. Operator implements two strategies too:
     *    3.1 delegates to the in-memory `other` relation if it is one
     *    3.2 loads itself into memory to make such a delegate call.
     *
     * TODO: The current strategy is not optimal at all, even if it basically
     * works. Possible optimizations include
     *
     * 1. implement comparison of ordered streams instead of 2.2. and 3.2.
     * 2. avoid the delegation complexity by extracting equals responsibility
     *    to another abstraction (?)
     */
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null || !(other instanceof Relation))
            return false;
        return equals((Relation) other);
    }

    public abstract boolean equals(Relation other);

}
