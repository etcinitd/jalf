package jalf.compiler;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;

/**
 * Parent (abstract) class of all relation implementations.
 */
public abstract class AbstractRelation implements Relation {

    @Override
    public Relation project(AttrList attributes) {
        // TODO implement
        return this;
    }

    @Override
    public Relation rename(Renaming renaming) {
        // TODO implement
        return this;
    }

    ///

    @Override
    public long count() {
        return stream().count();
    }

}
