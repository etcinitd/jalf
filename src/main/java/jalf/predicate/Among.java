package jalf.predicate;

import jalf.AttrName;
import jalf.Predicate;
import jalf.Tuple;

import static jalf.util.CollectionUtils.parallelStreamOf;

public class Among extends Predicate {
    private AttrName attrName;
    private Iterable<?> vals;

    public Among(AttrName attrName, Iterable<?> vals) {
        this.attrName = attrName;
        this.vals = vals;
    }

    @Override
    public boolean test(Tuple tuple) {
        Object attrVal = tuple.get(attrName);
        return parallelStreamOf(vals).anyMatch(val -> val.equals(attrVal));
    }
}
