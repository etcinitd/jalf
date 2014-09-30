package jalf.predicate;

import jalf.Predicate;
import jalf.Renaming;

public class Lte extends ComparisonPredicate<Comparable<Object>> {

    public Lte(Comparable<Object> left, Comparable<Object> right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r == null;
            else
                return l.compareTo(r) <= 0;
        });
    }

    @Override
    public Predicate rename(Renaming r) {
        return new Lte(renameOperand(left, r), renameOperand(right, r));
    }
}
