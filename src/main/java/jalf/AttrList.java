package jalf;

import static java.util.Collections.unmodifiableSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A list of attributes.
 *
 * AttrList actually captures an ordered set of attribute names, where the order
 * is the one provided at construction time (see factory methods). The order is
 * not significant when it comes to `equals` and `hashCode`; that is, the value
 * semantics is in terms of a set, not of a list. However, the order is
 * guaranteed when iterating attribute names. The order is mostly maintained for
 * convenience and principle of least surprise (POLS). Indeed, any concrete
 * syntax implemented in top of JAlf would lead users to dislike a pure set
 * semantics (think about a projection that would shuffle attributes); JAlf
 * implements a best effort strategy towards POLS.
 *
 * Instances of this class can be obtained either through factory methods or
 * through JAlf's DSL.
 */
public class AttrList implements Iterable<AttrName> {

    public static final AttrList EMPTY = new AttrList();

    private Set<AttrName> names;

    private AttrList(Set<AttrName> names) {
        this.names = unmodifiableSet(names);
    }

    private AttrList() {
        this(new HashSet<AttrName>());
    }

    public static Collector<AttrName, ?, AttrList> collector() {
        // TODO use a concurrent WeakHashMap to keep immutable AttrList(s)
        Function<Set<AttrName>, AttrList> finisher = (names) -> {
            return new AttrList(names);
        };
        return Collector.of(LinkedHashSet::new, Set<AttrName>::add, (left,
                right) -> {
            left.addAll(right);
            return left;
        }, finisher, Collector.Characteristics.CONCURRENT,
                Collector.Characteristics.UNORDERED);
    }

    /**
     * Builds an list from any stream of attribute names.
     *
     * @param attrNames
     *            a stream of attribute names.
     * @return an attribute list.
     */
    public static AttrList attrs(Stream<AttrName> attrNames) {
        return attrNames.collect(collector());
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames
     *            collection of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(Iterable<AttrName> attrNames) {
        return StreamSupport.stream(attrNames.spliterator(), false).collect(
                collector());
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames
     *            list of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(AttrName... attrNames) {
        return Stream.of(attrNames).collect(collector());
    }

    /**
     * Builds an attribute list from some attribute names.
     *
     * @pre attrNames should be distinct
     * @param attrNames
     *            list of attribute names
     * @return the built attribute list
     */
    public static AttrList attrs(String... attrNames) {
        return Stream.of(attrNames).map(AttrName::attr).collect(collector());
    }

    /**
     * Checks whether this attribute list contains `name`.
     *
     * @param name
     *            an attribute name.
     * @return true if this contains `name`, false otherwise.
     */
    public boolean contains(AttrName name) {
        return names.contains(name);
    }

    /**
     * Checks if this attribute list is the empty list.
     *
     * @return true if this attribute list contains not name at all, false
     *         otherwise.
     */
    public boolean isEmpty() {
        return names.isEmpty();
    }

    /**
     * Computes the difference between this and `other`.
     *
     * @param other
     *            another arbitrary attribute list.
     * @return another attributes list having all names in `this` but those also
     *         in `other`.
     */
    public AttrList difference(AttrList other) {
        LinkedHashSet<AttrName> diff = new LinkedHashSet<>(this.names);
        diff.removeAll(other.names);
        return new AttrList(diff);
    }

    /**
     * Computes the intersection of this and `other`.
     *
     * @param other
     *            another arbitrary attribute list.
     * @return another attributes list having all names in `this` and also in
     *         `other`.
     */
    public AttrList intersect(AttrList other) {
        LinkedHashSet<AttrName> intr = new LinkedHashSet<>(this.names);
        intr.retainAll(other.names);
        return new AttrList(intr);
    }

    /**
     * Computes the union of this and `other`.
     *
     * @param other
     *            another attribute list.
     * @return an attribute list haves names belonging to self or to other.
     */
    public AttrList union(AttrList other) {
        LinkedHashSet<AttrName> union = new LinkedHashSet<>(this.names);
        union.addAll(other.names);
        return new AttrList(union);
    }

    /**
     * Renames some attributes, returning the resulting attribute list.
     *
     * @param r
     *            a Renaming instance.
     * @return an attribute list with attributes renamed according to `r`.
     */
    public AttrList rename(Renaming r) {
        return AttrList.attrs(names.stream().map(a -> r.apply(a)));
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
        return "attrs("
                + names.stream().map(a -> a.getName())
                        .collect(Collectors.joining(", ")) + ")";
    }

    public List<AttrName> toList() {
        return names.stream().collect(Collectors.toList());
    }

}
