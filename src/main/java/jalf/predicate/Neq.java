package jalf.predicate;

import jalf.Predicate;
import jalf.Renaming;

public class Neq extends ComparisonPredicate<Object> {

    public Neq(Object left, Object right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r != null;
            else
                return !l.equals(r);
        });
    }

    @Override
    public Predicate rename(Renaming r) {
        return new Neq(renameOperand(left, r), renameOperand(right, r));
    }

    @Override
    public String toString() {
        return left.toString() + "!=" + right.toString();
    }
}
