package jalf.predicate;

import java.util.Set;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

public class False extends Predicate {

    public static final False INSTANCE = new False();

    private False() {
    }

    @Override
    public boolean test(Tuple t) {
        return false;
    }

    @Override
    protected void fillReferencedAttributes(Set<AttrName> attrNames) {
    }

    @Override
    public Predicate rename(Renaming renaming) {
        return this;
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        return new Pair<>(this, this);
    }

}
