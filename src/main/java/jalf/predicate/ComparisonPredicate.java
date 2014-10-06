package jalf.predicate;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

import java.util.Set;
import java.util.function.BiFunction;

public abstract class ComparisonPredicate<T> extends Predicate {
    protected T left;
    protected T right;
    private BiFunction<T, T, Boolean> op;

    protected ComparisonPredicate(T left, T right,
                                  BiFunction<T, T, Boolean> op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public boolean test(Tuple tuple) {
        T atLeft = getFor(left, tuple);
        T atRight = getFor(right, tuple);
        return op.apply(atLeft, atRight);
    }

    @Override
    public void fillReferencedAttributes(Set<AttrName> attrNames) {
        if (left instanceof AttrName)
          attrNames.add((AttrName)left);
        if (right instanceof AttrName)
          attrNames.add((AttrName)right);
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        AttrList references = getReferencedAttributes();
        AttrList invalidOnLeft = references.difference(list);
        if (invalidOnLeft.isEmpty()) {
            return new Pair<>(this, TRUE);
        } else {
            return new Pair<>(TRUE, this);
        }
    }

    // TODO Do we need to make this method protected?
    private T getFor(T what, Tuple tuple) {
        if (what instanceof AttrName) {
            // TODO How safe is it really?
            @SuppressWarnings("unchecked")
            T value = (T) tuple.get((AttrName) what);
            return value;
        } else {
            return what;
        }
    }

    protected T renameOperand(T op, Renaming r) {
        if (op instanceof AttrName)
            return (T) r.apply((AttrName) op);
        else
            return op;
    }

    @Override
    public int hashCode() {
        int hashCode = 31*getClass().hashCode();
        if (left != null)
            hashCode += left.hashCode();
        if (right != null)
            hashCode += right.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().equals(obj.getClass()))
            return false;
        ComparisonPredicate<?> other = (ComparisonPredicate<?>) obj;
        return left.equals(other.left) && right.equals(other.right);
    }
}
