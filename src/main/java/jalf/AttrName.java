package jalf;

import static jalf.util.ValidationUtils.validateNotNull;

/**
 * Name of an attribute.
 *
 * This abstraction is introduced to distinguish between attribute names and
 * data values in JAlf's public API (especially the DSL). Every abstraction
 * relying on attribute names announces this class in its API. When no confusion
 * is possible certain APIs conveniently allow String for attribute names.
 *
 * Instances of this class can be obtained using the `attr` factory method.
 */
public class AttrName implements Comparable<AttrName> {

    private String name;

    AttrName(String name) {
        validateNotNull("Parameter 'name' must be non-null.", name);
        this.name = name;
    }

    /**
     * Builds an attribute name from its string representation.
     *
     * @param name of the attribute
     * @return an AttrName instance for `name`
     */
    public static AttrName attr(String name) {
        // TODO use a concurrent WeakHashMap to keep immutable AttrName(s)
        return new AttrName(name);
    }

    /**
     * Dress `obj` as an attribute name, applying the necessary coercions.
     *
     * @pre the actual type of `obj` must match one the factory methods or
     *      be an AttrName already.
     * @param obj an object to dress as an attribute name.
     * @return obj coerced as an attribute name
     * @throws IllegalArgumentException if coercion fails.
     */
    public static AttrName dress(Object obj) {
        if (obj instanceof AttrName)
            return (AttrName) obj;
        if (obj instanceof String)
            return attr((String)obj);
        throw new IllegalArgumentException("Unable to dress `" + obj + "` as AttrName");
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(AttrName o) {
        return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || !(o instanceof AttrName))
            return false;
        AttrName attrName = (AttrName) o;
        return name.equals(attrName.name);
    }

    @Override
    public String toString() {
        return "attr(\"" + name + "\")";
    }
}
