package jalf;

import static java.util.Collections.unmodifiableSortedSet;

import java.util.Iterator;
import java.util.SortedSet;

/**
 * A list of attributes.
 *
 * AttrList actually captures an ordered set of attribute names. It is used,
 * for instance for `project(Relation, AttrList)`. Most of the time, the order
 * of attribute names does not matter but some operators may depend on it.
 * Their documentation will make that clear.
 *
 * This class is not intended to be used by end-users, please use Jalf's DSL
 * instead.
 */
public class AttrList implements Iterable<AttrName> {

    private SortedSet<AttrName> names;

    public AttrList(SortedSet<AttrName> names){
        this.names = unmodifiableSortedSet(names);
    }

    @Override
    public Iterator<AttrName> iterator() {
        return names.iterator();
    }

    @Override
    public int hashCode() {
        return names.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AttrList other = (AttrList) obj;
        return names.equals(other.names);
    }

}
