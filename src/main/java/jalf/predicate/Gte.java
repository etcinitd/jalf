package jalf.predicate;

public class Gte extends ComparisonPredicate<Comparable<Object>> {

    public Gte(Comparable<Object> left, Comparable<Object> right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r == null;
            else
                return l.compareTo(r) >= 0;
        });
    }

}
