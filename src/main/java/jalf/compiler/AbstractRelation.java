package jalf.compiler;

import jalf.AttrList;
import jalf.Relation;
import jalf.Renaming;
import jalf.relation.algebra.Project;
import jalf.relation.algebra.Rename;

/**
 * Parent (abstract) class of all relation implementations.
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

    ///

    @Override
    public long count() {
        return stream().count();
    }

}
