package jalf;

import static jalf.AttrName.attr;
import static jalf.util.ValidationUtils.validateNotNull;
import static java.util.Collections.unmodifiableMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        return intension(a -> attr(prefix + a.getName()));
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

        return intension(a -> attr(a.getName() + suffix));
    }

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

    }

    static class Intension extends Renaming {

        private UnaryOperator<AttrName> fn;

        Intension(UnaryOperator<AttrName> fn) {
            this.fn = fn;
        }

        @Override
        public AttrName apply(AttrName t) {
            return fn.apply(t);
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
