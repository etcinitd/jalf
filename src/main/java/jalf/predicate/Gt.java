package jalf.predicate;

import jalf.Predicate;
import jalf.Renaming;

public class Gt extends ComparisonPredicate<Comparable<Object>> {

    public Gt(Comparable<Object> left, Comparable<Object> right) {
        super(left, right, (l, r) -> {
            if (l == null || r == null)
                // TODO Is this correct?!
                return false;
            else
                return l.compareTo(r) > 0;
        });
    }

    @Override
    public Predicate rename(Renaming r) {
        return new Gt(renameOperand(left, r), renameOperand(right, r));
    }

    @Override
    public String toString() {
        return left.toString() + ">" + right.toString();
    }
}
