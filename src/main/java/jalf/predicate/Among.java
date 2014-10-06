package jalf.predicate;

import static jalf.util.CollectionUtils.parallelStreamOf;
import jalf.AttrList;
import jalf.AttrName;
import jalf.Predicate;
import jalf.Renaming;
import jalf.Tuple;
import jalf.util.Pair;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public void fillReferencedAttributes(Set<AttrName> attrNames) {
        attrNames.add(attrName);
    }

    @Override
    public Predicate rename(Renaming renaming) {
        return new Among(renaming.apply(attrName), vals);
    }

    @Override
    public Pair<Predicate> split(AttrList list) {
        if (list.contains(attrName)) {
            return new Pair<>(this, TRUE);
        } else {
            return new Pair<>(TRUE, this);
        }
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

    @Override
    public String toString() {
        String valsStr = StreamSupport.stream(vals.spliterator(), false)
            .map(Object::toString)
            .collect(Collectors.joining(","));
        return "among(" + attrName + "," + "[" + valsStr + "])";
    }

}
