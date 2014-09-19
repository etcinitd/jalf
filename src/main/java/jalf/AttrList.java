package jalf;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSortedSet;

/**
 * A list of attributes.
 *
 * AttrList actually captures an ordered set of attribute names. It is used,
 * for instance for `project(Relation, AttrList)`. Most of the time, the order
 * of attribute names does not matter but some operators may depend on it.
 * Their documentation will make that clear.
 *
 * Instances of this class can be obtained either through factory methods or
 * through JAlf's DSL.
 */
public class AttrList implements Iterable<AttrName> {

    private SortedSet<AttrName> names;

    private AttrList(SortedSet<AttrName> names){
        this.names = unmodifiableSortedSet(names);
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames list of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(AttrName... attrNames) {
        SortedSet<AttrName> set = Stream.of(attrNames)
            .distinct()
            .collect(Collectors.toCollection(TreeSet::new));
        return new AttrList(set);
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames list of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(String... attrNames) {
        SortedSet<AttrName> set = Stream.of(attrNames)
            .distinct()
            .map(AttrName::attr)
            .collect(Collectors.toCollection(TreeSet::new));
        return new AttrList(set);
    }

    public boolean contains(AttrName name) {
        return names.contains(name);
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
