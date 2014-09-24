package jalf.predicate;

import jalf.AttrName;
import jalf.Predicate;
import jalf.Tuple;

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
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean test(Tuple t) {
        return fn.test(t);
    }

}
