package jalf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static jalf.util.ValidationUtils.*;
import static java.util.Collections.unmodifiableMap;

/**
 * A tuple is an immutable set of attributes (i.e. name value pairs), with
 * no two pairs having the same name.
 *
 * This class implements the tuple abstraction in an immutable way. Methods
 * implementing the tuple algebra always compute a new Tuple instance without
 * modifying the receiver.
 *
 * Instances of this class can be obtained through static factory methods or
 * through JAlf's DSL. 
 */
public class Tuple {
    private final Map<AttrName, Object> attrs;
    private final Heading heading;

    private Tuple(Map<AttrName, Object> attrs) {
        this.attrs = unmodifiableMap(new ConcurrentHashMap<>(attrs));
        this.heading = constructHeading(attrs);
    }

    /**
     * Builds a tuple from a list of (attribute name, value) pairs.
     *
     * @pre keyValuePairs must have an even size. Odd elements must all be
     * attribute names.
     * @param keyValuePairs the list of pairs to convert to a tuple.
     * @return factored tuple.
     */
    public static Tuple varargs(Object... keyValuePairs) {
        validateNotNull("Parameter 'keyValuePairs' must be non-null.", keyValuePairs);
        validate("Length of key-value pairs must be even.", keyValuePairs.length % 2, 0);

        Map<AttrName, Object> attrs = new ConcurrentHashMap<>();
        for (int i = 0; i < keyValuePairs.length; i++) {
            Object key = keyValuePairs[i++];
            AttrName attr = validateCast("Attribute name must be an AttrName.", key, AttrName.class);
            Object value = keyValuePairs[i];
            attrs.put(attr, value);
        }
        return new Tuple(attrs);
    }

    public Heading heading() {
        return heading;
    }

    private Heading constructHeading(Map<AttrName, Object> attrs) {
        Map<AttrName, AttrType> attrTypes = new ConcurrentHashMap<>();
        attrs.forEach((name, val) -> attrTypes.put(name, AttrType.typeOf(val)));
        return Heading.headingOf(attrTypes);
    }

    /**
     * Returns the value associated to an attribute name.
     *
     * @pre attrName must belong to tuple's attributes.
     * @param attrName an attribute name.
     * @return the value associated with the specified attribute name.
     */
    public Object get(AttrName attrName) {
        return attrs.get(attrName);
    }

    /**
     * Returns a projection of this tuple on some of its attributes.
     *
     * @pre `on` must be a subset of this tuple's attributes.
     * @param on a list of attributes to project on.
     * @return a projection of this tuple on attributes specified in `on`.
     */
    public Tuple project(AttrList on) {
        Map<AttrName, Object> p = new ConcurrentHashMap<>();
        on.forEach(attrName -> p.put(attrName, attrs.get(attrName)));
        return new Tuple(p);
    }

    /**
     * Returns a new tuple by renaming some attributes.
     *
     * @param r renaming function mapping old to new attribute names.
     * @return the renamed tuple.
     */
    public Tuple rename(Renaming r) {
        Map<AttrName, Object> renamed = new ConcurrentHashMap<>();
        attrs.entrySet().forEach(attribute -> {
            AttrName attrName = attribute.getKey();
            AttrName as = r.apply(attrName);
            renamed.put(as, attribute.getValue());
        });
        return new Tuple(renamed);
    }

    public Tuple join(Tuple tuple) {
        Map<AttrName, Object> joined = new ConcurrentHashMap<>(attrs);
        joined.putAll(tuple.attrs);
        return new Tuple(joined);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return attrs.equals(tuple.attrs);
    }

    @Override
    public int hashCode() {
        return attrs.hashCode();
    }

    @Override
    public String toString() {
        String result = "tuple(";
        for (Map.Entry<AttrName, Object> entry : attrs.entrySet()) {
            if (result.length() > 6)
                result += ", ";
            result += '"' + entry.getKey().getName() + '"';
            result += ", ";
            result += entry.getValue();
        }
        return result + ")";
    }
}
