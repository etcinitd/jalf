package jalf.predicate;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

import java.util.Set;

public class JavaPredicate extends Predicate {

    private java.util.function.Predicate<Tuple> fn;

    public JavaPredicate(java.util.function.Predicate<Tuple> fn) {
        this.fn = fn;
    }

    @Override
    public boolean isStaticallyAnalyzable() {
        return false;
    }

    @Override
    protected void fillReferencedAttributes(Set<AttrName> attrNames) {
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        return new Pair<>(TRUE, this);
    }

    @Override
    public boolean test(Tuple t) {
        return fn.test(t);
    }

    @Override
    public Predicate rename(Renaming renaming) {
        throw new UnsupportedOperationException();
    }

}
