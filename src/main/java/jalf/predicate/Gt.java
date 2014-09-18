package jalf.predicate;

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

}
