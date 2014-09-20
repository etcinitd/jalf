package jalf.type;

import static jalf.util.ValidationUtils.validate;
import static jalf.util.ValidationUtils.validateNotNull;
import jalf.AttrList;
import jalf.AttrName;
import jalf.Renaming;
import jalf.Type;
import jalf.TypeException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A heading is a set of attribute (name, type) pairs, under the constraints
 * that no two attributes have the same name.
 *
 * This class is introduced as an helper to implement TupleType and RelationType
 * and is not intended to be instantiated directly.
 */
public class Heading {

    private Map<AttrName, Type<?>> attributes;

    protected Heading(Map<AttrName, Type<?>> attributes) {
        this.attributes = attributes;
    }

    /**
     * Builds a heading from an inline list of attribute (name, type) pairs.
     *
     * @pre pairs must have an even size. Odd elements must be attribute names,
     * even elements must be types.
     * @param nameTypePairs the list of pairs to convert to a heading.
     * @return factored heading.
     */
    public static Heading varargs(Object... nameTypePairs) {
        validateNotNull("Parameter 'keyValuePairs' must be non-null.", nameTypePairs);
        validate("Length of key-value pairs must be even.", nameTypePairs.length % 2, 0);

        Map<AttrName, Type<?>> attrs = new HashMap<>();
        for (int i = 0; i < nameTypePairs.length; i++) {
            AttrName attr = AttrName.dress(nameTypePairs[i++]);
            Type<?> type = Type.dress(nameTypePairs[i]);
            attrs.put(attr, type);
        }
        return new Heading(attrs);
    }

    /**
     * Infers the heading from a list of attribute (name, value) pairs.
     *
     * @pre pairs must have an even size. Odd elements must be attribute names.
     * @param keyValuePairs the list of pairs from which to infer a heading.
     * @return a Heading instance.
     */
    public static Heading infer(Map<AttrName, Object> nameValuePairs) {
        Map<AttrName, Type<?>> attributes = new HashMap<>();
        nameValuePairs.forEach((attr, value) -> {
            Type<?> type = Type.infer(value);
            attributes.put(attr, type);
        });
        return new Heading(attributes);
    }

    /**
     * Computes the least common super heading (lcsh) of `h1` and `h2`. The two
     * headings must have same attribute names, but may have a different type
     * for a given one. `lcsh` is defined as the heading with same set of
     * attribute names, each mapped to the least common super type.
     *
     * @pre h1 and h2 may have same attribute names.
     * @param h1 a Heading instance.
     * @param h2 another Heading instance.
     * @return the least common super heading between `h1` and `h2`.
     * @throws TypeException when h1 and h2 do not have same attribute names or
     * no least common super type exists for one of them.
     */
    public static Heading leastCommonSuperHeading(Heading h1, Heading h2) throws TypeException {
        Map<AttrName, Type<?>> left =  h1.attributes;
        Set<AttrName> leftAttrs = left.keySet();
        Map<AttrName, Type<?>> right = h2.attributes;
        Set<AttrName> rightAttrs = right.keySet();

        // TODO: is there a stream-minded way of doing this merge?
        if (leftAttrs.equals(rightAttrs)) {
            Map<AttrName, Type<?>> result = new HashMap<>();
            left.forEach((attr, leftType) -> {
                Type<?> rightType = right.get(attr);
                Type<?> least = Type.leastCommonSupertype(leftType, rightType);
                result.put(attr, least);
            });
            return new Heading(result);
        } else {
            throw new TypeException("Headings must have same attributes");
        }
    }

    /**
     * Returns the type mapped to an attribute name, null if no such type.
     *
     * @param attrName an attribute name.
     * @return the type associated to `attrName`, or null.
     */
    public Type<?> getTypeOf(AttrName attrName) {
        return attributes.get(attrName);
    }

    /**
     * Projects this heading on a subset of its attributes captured by `on`.
     *
     * @pre `on` should be a subset of the heading attribute names.
     * @param on a set of attribute names.
     * @return the heading obtained by keeping only attributes in `on`.
     */
    public Heading project(AttrList on) {
        Map<AttrName, Type<?>> projected = new HashMap<>();
        attributes.forEach((name, type) -> {
            if (on.contains(name))
                projected.put(name, type);
        });
        return new Heading(projected);
    }

    /**
     * Computes the heading obtained by renaming some of the attributes using
     * `renaming`.
     *
     * @param renaming a renaming function.
     * @return the heading obtained by renaming the attributes.
     */
    public Heading rename(Renaming renaming) {
        Map<AttrName, Type<?>> renamed = new HashMap<>();
        attributes.forEach((name, type) -> renamed.put(renaming.apply(name), type));
        return new Heading(renamed);
    }

    @Override
    public int hashCode() {
        return attributes.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || !(obj instanceof Heading))
            return false;
        Heading other = (Heading) obj;
        return attributes.equals(other.attributes);
    }

}