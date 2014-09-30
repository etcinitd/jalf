package jalf.predicate;

import java.util.Set;

import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
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

    @Override
    protected void fillReferencedAttributes(Set<AttrName> attrNames) {
        attrNames.add(attrName);
    }

    @Override
    public Predicate rename(Renaming renaming) {
        return new Among(renaming.apply(attrName), vals);
    }

    @Override
    public int hashCode() {
        return 31*attrName.hashCode() + vals.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Among))
            return false;
        Among other = (Among) obj;
        return attrName.equals(other.attrName) && vals.equals(other.vals);
    }

}
