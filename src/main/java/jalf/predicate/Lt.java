package jalf.predicate;

import jalf.Predicate;
import jalf.Renaming;

public class Lt extends ComparisonPredicate<Comparable<Object>> {

    public Lt(Comparable<Object> left, Comparable<Object> right) {
        super(left, right, (l, r) -> {
            if (l == null || r == null)
                // TODO Is this correct?!
                return false;
            else
                return l.compareTo(r) < 0;
        });
    }

    @Override
    public Predicate rename(Renaming r) {
        return new Lt(renameOperand(left, r), renameOperand(right, r));
    }
}
