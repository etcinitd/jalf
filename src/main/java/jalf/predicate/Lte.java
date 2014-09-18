package jalf.predicate;

public class Lte extends ComparisonPredicate<Comparable<Object>> {

    public Lte(Comparable<Object> left, Comparable<Object> right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r == null;
            else
                return l.compareTo(r) <= 0;
        });
    }

}
