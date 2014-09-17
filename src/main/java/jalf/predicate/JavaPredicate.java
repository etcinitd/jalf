package jalf.predicate;

import jalf.Predicate;
import jalf.Tuple;

public class JavaPredicate extends Predicate {

    private java.util.function.Predicate<Tuple> fn;

    public JavaPredicate(java.util.function.Predicate<Tuple> fn) {
        this.fn = fn;
    }

    @Override
    public boolean test(Tuple t) {
        return fn.test(t);
    }

}
