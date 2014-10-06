package jalf.predicate;

import jalf.Predicate;
import jalf.Renaming;

public class Gte extends ComparisonPredicate<Comparable<Object>> {

    public Gte(Comparable<Object> left, Comparable<Object> right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r == null;
            else
                return l.compareTo(r) >= 0;
        });
    }

    @Override
    public Predicate rename(Renaming r) {
        return new Gte(renameOperand(left, r), renameOperand(right, r));
    }

    @Override
    public String toString() {
        return left.toString() + ">=" + right.toString();
    }
}
