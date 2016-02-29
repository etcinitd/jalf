package jalf;

import static jalf.util.CollectionUtils.rekey;
import static jalf.util.ValidationUtils.validate;
import static jalf.util.ValidationUtils.validateNotNull;
import static java.util.Collections.unmodifiableMap;
import jalf.type.TupleType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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

    private final TupleType type;

    public Tuple(TupleType type, Map<AttrName, Object> attrs) {
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
    public static Tuple dress(Object... keyValuePairs) {
        validateNotNull("Parameter 'keyValuePairs' must be non-null.", keyValuePairs);
        validate("Length of key-value pairs must be even.", keyValuePairs.length % 2, 0);

        Map<AttrName, Object> attrs = new ConcurrentHashMap<>();
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
     * Fetches some attribute values and returns them as a list.
     *
     * @pre attrNames should only contain existing attributes.
     * @param attrNames some attribute names.
     * @return the list of values corresponding to the attribute names.
     */
    public List<Object> fetch(AttrList attrNames) {
        return attrNames.stream()
                .map(a -> attrs.get(a))
                .collect(Collectors.toList());
    }

    /**
     * Returns a projection of this tuple on some of its attributes.
     *
     * Note: despite its visibility, this method is part of JAlf protected API.
     * It should not be used by end users.
     * TODO: how to fix this without hurting performance too much?
     *
     * @pre `on` must be a subset of this tuple's attributes.
     * @pre `resultingType` should be faithful to the actual result, that it
     * it must guarantee the post condition.
     * @param on a list of attributes to project on.
     * @return a projection of this tuple on attributes specified in `on`.
     * @post `resultingType.contains(project(on, resultingType))` is true
     */
    public Tuple project(AttrList on, TupleType resultingType) {
        Map<AttrName, Object> p = new ConcurrentHashMap<>();
        on.forEach(attrName -> p.put(attrName, attrs.get(attrName)));
        return new Tuple(resultingType, p);
    }

    /**
     * Returns a new tuple by renaming some attributes.
     *
     * Note: despite its visibility, this method is part of JAlf protected API.
     * It should not be used by end users.
     * TODO: how to fix this without hurting performance too much?
     *
     * @param r renaming function mapping old to new attribute names.
     * @pre `resultingType` should be faithful to the actual result, that it
     * it must guarantee the post condition.
     * @return the renamed tuple.
     * @post `resultingType.contains(rename(r, resultingType))` is true
     */
    public Tuple rename(Renaming r, TupleType resultingType) {
        return new Tuple(resultingType, rekey(attrs, (k,v) -> r.apply(k)));
    }

    /**
     * Joins this tuple with another one.
     *
     * Note: despite its visibility, this method is part of JAlf protected API.
     * It should not be used by end users.
     * TODO: how to fix this without hurting performance too much?
     *
     * @pre this and other should agree on values of common attributes.
     * @pre `resultingType` should be faithful to the actual result, that it
     * it must guarantee the post condition.
     * @param other another tuple to join on.
     * @param resultingType the type of the result.
     * @return the joined tuple.
     * @post `resultingType.contains(join(other, resultingType))` is true
     */
    public Tuple join(Tuple other, TupleType resultingType) {
        Map<AttrName, Object> joined = new ConcurrentHashMap<>(attrs);
        joined.putAll(other.attrs);
        return new Tuple(resultingType, joined);
    }

    public boolean isEmpty() {
        return this.attrs.isEmpty();
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
