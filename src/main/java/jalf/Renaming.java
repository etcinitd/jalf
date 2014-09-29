package jalf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jalf.AttrName.attr;
import static jalf.util.ValidationUtils.validateNotNull;
import static java.util.Collections.unmodifiableMap;

/**
 * Captures the renaming of some attribute names.
 *
 * Conceptually a renaming (used by `Relation#rename`) is a total function
 * mapping attribute names to attribute names (hence this class implementing
 * UnaryOperator<AttrName>). Such a mapping can be expressed by extension (i.e.
 * by making all pairs explicit) or by intension (i.e. using an arbitrary
 * function). Various static factory methods provide instances of this class.
 */
public abstract class Renaming implements UnaryOperator<AttrName> {

    // DSL information contracts

    /**
     * Builds a Renaming instance by extension, using a map of attribute names.
     * The resulting function is made total through the identify function.
     *
     * @param attrNames before/after mapping of attribute name renaming. 
     * @return a Renaming instance implementing the total function.
     */
    public static Renaming extension(Map<AttrName, AttrName> attrNames) {
        validateNotNull("Parameter 'attrNames' must be non-null.", attrNames);

        return new Extension(attrNames);
    }

    /**
     * Builds a Renaming instance by extension, from a list of attribute name
     * pairs.
     *
     * @param attrNames a sequence of (before, after) attribute names.
     * @return a Renaming instance implementing the total function.
     */
    public static Renaming extension(List<AttrName> attrNames) {
        validateNotNull("Parameter 'attrNames' must be non-null.", attrNames);

        Map<AttrName, AttrName> renamingMap = new HashMap<>();
        for (int i = 0; i < attrNames.size(); i++) {
            AttrName currentName = attrNames.get(i++);
            AttrName mappedName = attrNames.get(i);
            renamingMap.put(currentName, mappedName);
        }
        return extension(renamingMap);
    }

    /**
     * Convenient shortcut over `explicit(List<AttrName>)`.
     */
    public static Renaming extension(AttrName...attrNames) {
        validateNotNull("Parameter 'attrNames' must be non-null.", attrNames);

        return extension(Arrays.asList(attrNames));
    }

    /**
     * Convenient shortcut over `explicit(AttrName[])`.
     */
    public static Renaming extension(String...attrNames) {
        validateNotNull("Parameter 'attrNames' must be non-null.", attrNames);

        List<AttrName> attrs = Stream.of(attrNames)
                .map(AttrName::attr)
                .collect(Collectors.toList());
        return extension(attrs);
    }

    /**
     * Builds a Renaming instance by an intension, using a user function and
     * its inverse.
     *
     * @param fn a total function from AttrName -> AttrName.
     * @param nf the reverse function.
     * @return A decoration of `fn` as a proper Renaming instance.
     */
    public static Renaming intension(UnaryOperator<AttrName> fn, UnaryOperator<AttrName> nf) {
        validateNotNull("Parameter 'fn' must be non-null.", fn);
        validateNotNull("Parameter 'nf' must be non-null.", nf);

        return new Intension(fn, nf);
    }

    /**
     * Builds a Renaming instance by an intension, using a user function.
     *
     * @param fn a total function from AttrName -> AttrName.
     * @return A decoration of `fn` as a proper Renaming instance.
     */
    public static Renaming intension(UnaryOperator<AttrName> fn) {
        validateNotNull("Parameter 'fn' must be non-null.", fn);

        if (fn instanceof Renaming) return (Renaming) fn;
        return new Intension(fn);
    }

    /**
     * Builds a Renaming instance that adds the same prefix on every attribute
     * name.
     *
     * @param prefix the prefix to apply to attribute names.
     * @return A Renaming instance implementing the function.
     */
    public static Renaming prefix(String prefix) {
        validateNotNull("Parameter 'prefix' must be non-null.", prefix);

        UnaryOperator<AttrName> fn = a -> attr(prefix + a.getName());
        UnaryOperator<AttrName> nf = a -> {
            String name = a.getName();
            return attr(name.substring(prefix.length()));
        };
        return intension(fn, nf);
    }

    /**
     * Builds a Renaming instance that adds the same suffix on every attribute
     * name.
     *
     * @param suffix the suffix to apply to attribute names.
     * @return A Renaming instance implementing the function.
     */
    public static Renaming suffix(String suffix) {
        validateNotNull("Parameter 'suffix' must be non-null.", suffix);

        UnaryOperator<AttrName> fn = a -> attr(a.getName() + suffix);
        UnaryOperator<AttrName> nf = a -> {
            String name = a.getName();
            int length = a.getName().length();
            return attr(name.substring(0, length - suffix.length()));
        };
        return intension(fn, nf);
    }

    /**
     * Checks whether this renaming is reversible.
     *
     * @return true if this renaming is reversible, false otherwise.
     */
    public abstract boolean isReversible();

    /**
     * Returns the inverse renaming of this.
     *
     * @pre the renaming must be reversible.
     * @return a Renaming instance that applies the inverse transformation to
     * attribute names.
     */
    public abstract Renaming reverse();

    /**
     * Projects this Renaming function by restricting its domain to a subset
     * of its attributes.
     *
     * When the renaming is not an extension, this methods may simply return
     * the source renaming itself, that is, not applying a domain restriction
     * per se but a compatible function.
     *
     * Also, attributes should already be a subset of the function domain; this
     * method is however robust to violations of that precondition.
     *
     * @param attributes some attribute names.
     * @return another Renaming instance with a restricted domain.
     */
    public abstract Renaming project(AttrList attributes);

    static class Extension extends Renaming {

        private Map<AttrName, AttrName> renamings;

        Extension(Map<AttrName, AttrName> renamings) {
            this.renamings = unmodifiableMap(renamings);
        }

        @Override
        public AttrName apply(AttrName t) {
            if (renamings.containsKey(t))
                return renamings.get(t);
            else
                return t;
        }

        @Override
        public boolean isReversible() {
            return true;
        }

        @Override
        public Renaming reverse() {
            Map<AttrName, AttrName> reverseMap = new HashMap<>();
            renamings.forEach((a,b) -> reverseMap.put(b, a));
            return new Extension(reverseMap);
        }

        public Renaming project(AttrList attributes) {
            Map<AttrName, AttrName> r = new HashMap<>();
            attributes.forEach(a -> {
                AttrName renamed = renamings.get(a);
                if (renamed != null) {
                    r.put(a, renamed);
                }
            });
            return new Extension(r);
        }

        @Override
        public int hashCode() {
            return 31*Extension.class.hashCode() + renamings.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Extension other = (Extension) obj;
            return renamings.equals(other.renamings);
        }

        @Override
        public String toString() {
            return "renaming(" + renamings + ")";
        }

    }

    static class Intension extends Renaming {

        private UnaryOperator<AttrName> fn;
        private UnaryOperator<AttrName> nf;

        Intension(UnaryOperator<AttrName> fn, UnaryOperator<AttrName> nf) {
            this.fn = fn;
            this.nf = nf;
        }

        public Intension(UnaryOperator<AttrName> fn) {
            this(fn, null);
        }

        @Override
        public AttrName apply(AttrName t) {
            return fn.apply(t);
        }

        @Override
        public boolean isReversible() {
            return (nf != null);
        }

        @Override
        public Renaming reverse() {
            if (nf == null) {
                throw new UnsupportedOperationException();
            } else {
                return new Intension(nf, fn);
            }
        }

        @Override
        public Renaming project(AttrList attributes) {
            return this;
        }

        @Override
        public int hashCode() {
            return 31*Intension.class.hashCode() + fn.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Intension other = (Intension) obj;
            return fn.equals(other.fn);
        }

    }

}
