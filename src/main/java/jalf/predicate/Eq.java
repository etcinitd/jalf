package jalf.predicate;

public class Eq extends ComparisonPredicate<Object> {

    public Eq(Object left, Object right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r == null;
            else
                return l.equals(r);
        });
    }

}
