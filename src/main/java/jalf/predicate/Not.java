package jalf.predicate;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

import java.util.Set;

public class Not extends Predicate {

    private Predicate inner;

    public Not(Predicate inner) {
        this.inner = inner;
    }

    @Override
    public boolean test(Tuple t) {
        return !inner.test(t);
    }

    public Predicate not() {
        return inner;
    }

    @Override
    public void fillReferencedAttributes(Set<AttrName> attrNames) {
        inner.fillReferencedAttributes(attrNames);
    }

    @Override
    public Predicate rename(Renaming renaming) {
        return new Not(inner.rename(renaming));
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        // let's try it the optimistic way
        Pair<Predicate> innerSplit = inner.split(list);

        // if inner predicate was able to eat everything on left, we are
        // safe: not(left & TRUE) = not(left) & TRUE
        if (TRUE.equals(innerSplit.right)) {
            return new Pair<>(this, TRUE);
        }

        // not(x or y or z) => not(x) and not(y) and not(z)
        // which is further optimizable.
        if (inner instanceof Or) {
            return ((Or)inner).negate().split(list);
        }

        return new Pair<>(TRUE, this);
    }

    @Override
    public int hashCode() {
        return 31*inner.hashCode() + getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || !(obj instanceof Not))
            return false;
        Not other = (Not) obj;
        return inner.equals(other.inner);
    }

    @Override
    public String toString() {
        return "not(" + inner.toString() + ")";
    }
}
