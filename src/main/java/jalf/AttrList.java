package jalf;

import static jalf.util.CollectionUtils.parallelStreamOf;
import static java.util.Collections.unmodifiableSortedSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * Builds an list from any stream of attribute names.
     *
     * @param attrNames a stream of attribute names.
     * @return an attribute list.
     */
    public static AttrList stream(Stream<AttrName> attrNames) {
        SortedSet<AttrName> set = attrNames
                .distinct()
                .collect(Collectors.toCollection(TreeSet::new));
        return new AttrList(set);
    }

    /**
     * Builds an list from any collection of attribute names.
     *
     * @param attrNames a collection of attribute names.
     * @return an attribute list.
     */
    public static AttrList collection(Collection<AttrName> attrNames) {
        return stream(attrNames.stream());
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
        return stream(Stream.of(attrNames));
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames list of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(String... attrNames) {
        return stream(Stream.of(attrNames).map(AttrName::attr));
    }

    /**
     * Checks whether this attribute list contains `name`.
     *
     * @param name an attribute name.
     * @return true if this contains `name`, false otherwise.
     */
    public boolean contains(AttrName name) {
        return names.contains(name);
    }

    /**
     * Checks if this attribute list is the empty list.
     *
     * @return true if this attribute list contains not name at all, false
     * otherwise.
     */
    public boolean isEmpty() {
        return names.isEmpty();
    }

    /**
     * Computes the difference between this and `other`.
     *
     * @param other another arbitrary attribute list.
     * @return another attributes list having all names in `this` but those
     * also in `other`.
     */
    public AttrList difference(AttrList other) {
        SortedSet<AttrName> diff = new TreeSet<>(this.names);
        diff.removeAll(other.names);
        return new AttrList(diff);
    }

    /**
     * Computes the intersection of this and `other`.
     *
     * @param other another arbitrary attribute list.
     * @return another attributes list having all names in `this` and also in
     * `other`.
     */
    public AttrList intersect(AttrList other) {
        SortedSet<AttrName> intr = new TreeSet<>(this.names);
        intr.retainAll(other.names);
        return AttrList.attrs(intr);
    }

    @Override
    public Iterator<AttrName> iterator() {
        return names.iterator();
    }

    public Stream<AttrName> stream() {
        return names.stream();
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

    @Override
    public String toString() {
        return "attrs(" + names.stream()
                .map(a -> a.getName())
                .collect(Collectors.joining(", "))
                + ")";
    }

}
