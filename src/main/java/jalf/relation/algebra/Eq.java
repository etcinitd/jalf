package jalf.relation.algebra;

import jalf.AttrName;
import jalf.Tuple;

public class Eq extends Predicate {
    private AttrName attrName;
    private Object value;

    public Eq(AttrName attrName, Object value) {
        this.attrName = attrName;
        this.value = value;
    }

    @Override
    public boolean test(Tuple tuple) {
        return tuple.get(attrName).equals(value);
    }
}
