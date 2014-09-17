package jalf.predicate;

import jalf.AttrName;
import jalf.Predicate;
import jalf.Tuple;

public class Eq extends Predicate {
    private Object left;
    private Object right;

    public Eq(Object left, Object right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean test(Tuple tuple) {
        Object atLeft = getFor(left, tuple);
        Object atRight = getFor(right, tuple);
        if (atLeft == null)
            return atRight == null;
        else
            return atLeft.equals(atRight);
    }

    private Object getFor(Object what, Tuple tuple) {
        if (what instanceof AttrName)
            return tuple.get((AttrName)what);
        else
            return what;
    }
}
