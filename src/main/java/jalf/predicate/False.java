package jalf.predicate;

import java.util.Set;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

public class False extends Predicate {

    public static False INSTANCE;

    private False() {
    }

    public static synchronized Predicate instance() {
        if (INSTANCE == null) {
            INSTANCE = new False();
        }
        return INSTANCE;
    }

    @Override
    public Predicate and(Predicate other) {
        return this;
    }

    public Predicate not() {
        return TRUE;
    }

    @Override
    public boolean test(Tuple t) {
        return false;
    }

    @Override
    public void fillReferencedAttributes(Set<AttrName> attrNames) {
    }

    @Override
    public Predicate rename(Renaming renaming) {
        return this;
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        return new Pair<>(this, this);
    }

    @Override
    public String toString() {
        return "false";
    }
}
