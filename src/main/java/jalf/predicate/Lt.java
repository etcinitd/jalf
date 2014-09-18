package jalf.predicate;

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

}
