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

public class Heading {

    private Map<AttrName, Type<?>> attributes;

    public Heading(Map<AttrName, Type<?>> attributes) {
        this.attributes = attributes;
    }

    public static Heading varargs(Object... keyValuePairs) {
        validateNotNull("Parameter 'keyValuePairs' must be non-null.", keyValuePairs);
        validate("Length of key-value pairs must be even.", keyValuePairs.length % 2, 0);

        Map<AttrName, Type<?>> attrs = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i++) {
            AttrName attr = AttrName.dress(keyValuePairs[i++]);
            Type<?> type = Type.dress(keyValuePairs[i]);
            attrs.put(attr, type);
        }
        return new Heading(attrs);
    }

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
            throw new IllegalArgumentException("Headings must have same attributes");
        }
    }

    public static Heading infer(Map<AttrName, Object> keyValuePairs) {
        Map<AttrName, Type<?>> attributes = new HashMap<>();
        keyValuePairs.forEach((attr, value) -> {
            Type<?> type = Type.infer(value);
            attributes.put(attr, type);
        });
        return new Heading(attributes);
    }

    public Type<?> getTypeOf(AttrName attrName) {
        return attributes.get(attrName);
    }

    public Heading project(AttrList on) {
        Map<AttrName, Type<?>> projected = new HashMap<>();
        attributes.forEach((name, type) -> {
            if (on.contains(name))
                projected.put(name, type);
        });
        return new Heading(projected);
    }

    public Heading rename(Renaming renaming) {
        Map<AttrName, Type<?>> renamed = new HashMap<>();
        attributes.forEach((name, type) -> renamed.put(renaming.apply(name), type));
        return new Heading(renamed);
    }

}
