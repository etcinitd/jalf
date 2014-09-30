package jalf.predicate;

import jalf.Predicate;
import jalf.Renaming;

public class Eq extends ComparisonPredicate<Object> {

    public Eq(Object left, Object right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r == null;
            else
                return l.equals(r);
        });
    }

    @Override
    public Predicate rename(Renaming r) {
        return new Eq(renameOperand(left, r), renameOperand(right, r));
    }
}
