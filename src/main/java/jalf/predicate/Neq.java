package jalf.predicate;

public class Neq extends ComparisonPredicate<Object> {

    public Neq(Object left, Object right) {
        super(left, right, (l, r) -> {
            if (l == null)
                return r != null;
            else
                return !l.equals(r);
        });
    }

}
