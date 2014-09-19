package jalf;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jalf.util.CollectionUtils.parallelStreamOf;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSortedSet;
import static java.util.stream.Collectors.toList;

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
     * @param attrNames collection of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(Iterable<AttrName> attrNames) {
        // TODO use a concurrent WeakHashMap to keep immutable AttrList(s)
        SortedSet<AttrName> set = parallelStreamOf(attrNames)
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
    public static AttrList attrs(AttrName... attrNames) {
        return attrs(asList(attrNames));
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames list of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(String... attrNames) {
        List<AttrName> iterable = Stream.of(attrNames)
                .distinct()
                .map(AttrName::attr)
                .collect(toList());
        return attrs(iterable);
    }

    @Override
    public Iterator<AttrName> iterator() {
        return names.iterator();
    }

    public boolean contains(AttrName name) {
        return names.contains(name);
    }

    public AttrList intersect(AttrList other) {
        Set<AttrName> common = new HashSet<>();
        for (AttrName name : names) {
            if (other.names.contains(name)) {
                common.add(name);
            }
        }
        return AttrList.attrs(common);
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
