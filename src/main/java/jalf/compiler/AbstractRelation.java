package jalf.compiler;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.Tuple;
import jalf.relation.algebra.Predicate;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;
import jalf.relation.algebra.Restrict;

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

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (other == null || !(other instanceof Relation))
            return false;
        return equals((Relation) other);
    }

    public abstract boolean equals(Relation other);

}
