package jalf;

import static jalf.util.ValidationUtils.validate;
import static jalf.util.ValidationUtils.validateNotNull;
import static java.util.Collections.unmodifiableMap;
import jalf.type.TupleType;

import java.util.HashMap;
import java.util.Map;

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
    private Map<AttrName, Object> attrs;
    private TupleType type;

    private Tuple(TupleType type, Map<AttrName, Object> attrs) {
        this.type = type;
        this.attrs = unmodifiableMap(attrs);
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

        Map<AttrName, Object> attrs = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i++) {
            AttrName attr = AttrName.dress(keyValuePairs[i++]);
            Object value = keyValuePairs[i];
            attrs.put(attr, value);
        }
        return new Tuple(TupleType.infer(attrs), attrs);
    }

    /**
     * Returns the type of this tuple.
     *
     * @return the type of this tuple as a TupleType instance.
     */
    public TupleType getType() {
        return type;
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
    public Tuple project(AttrList on, TupleType resultingType) {
        Map<AttrName, Object> p = new HashMap<>();
        on.forEach(attrName -> p.put(attrName, attrs.get(attrName)));
        return new Tuple(resultingType, p);
    }

    /**
     * Returns a new tuple by renaming some attributes.
     *
     * @param r renaming function mapping old to new attribute names.
     * @return the renamed tuple.
     */
    public Tuple rename(Renaming r, TupleType resultingType) {
        Map<AttrName, Object> renamed = new HashMap<>();
        attrs.entrySet().forEach(attribute -> {
            AttrName attrName = attribute.getKey();
            AttrName as = r.apply(attrName);
            renamed.put(as, attribute.getValue());
        });
        return new Tuple(resultingType, renamed);
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
