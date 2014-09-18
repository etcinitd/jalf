package jalf.predicate;

import jalf.AttrName;
import jalf.Predicate;
import jalf.Tuple;

import java.util.function.BiFunction;

public abstract class ComparisonPredicate<T> extends Predicate {
    protected T left;
    protected T right;
    private BiFunction<T, T, Boolean> op;

    protected ComparisonPredicate(T left, T right,
                                  BiFunction<T, T, Boolean> op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public boolean test(Tuple tuple) {
        T atLeft = getFor(left, tuple);
        T atRight = getFor(right, tuple);
        return op.apply(atLeft, atRight);
    }

    // TODO Do we need to make this method protected?
    private T getFor(T what, Tuple tuple) {
        if (what instanceof AttrName) {
            // TODO How safe is it really?
            @SuppressWarnings("unchecked")
            T value = (T) tuple.get((AttrName) what);
            return value;
        } else {
            return what;
        }
    }
}
