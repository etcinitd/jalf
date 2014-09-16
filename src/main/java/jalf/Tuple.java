package jalf;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

import static jalf.util.ValidationUtils.*;
import static java.util.Collections.unmodifiableMap;

/**
 * A tuple is an immutable set of attributes (i.e. name value pairs), with
 * no two pairs having the same name.
 */
public class Tuple {
    private Map<AttrName, Object> attrs;

    public Tuple(Map<AttrName, Object> attrs) {
        this.attrs = unmodifiableMap(attrs);
    }

    public Tuple(Object... keyValuePairs) {
        validateNotNull("Parameter 'keyValuePairs' must be non-null.", keyValuePairs);
        validate("Length of key-value pairs must be even.", keyValuePairs.length % 2, 0);
        attrs = new HashMap<>();
        if (keyValuePairs.length == 0) {
            return;
        }

        for (int i = 0; i < keyValuePairs.length; i++) {
            Object key = keyValuePairs[i++];
            String keyStr = validateCast("Attribute name must be a string.", key, String.class);
            Object value = keyValuePairs[i];
            attrs.put(AttrName.attr(keyStr), value);
        }
    }

    public Tuple project(AttrList on) {
        Map<AttrName, Object> p = new HashMap<>();
        on.forEach(attrName -> p.put(attrName, attrs.get(attrName)));
        return new Tuple(p);
    }

    public Tuple rename(UnaryOperator<AttrName> r) {
        Map<AttrName, Object> renamed = new HashMap<>();
        attrs.entrySet().forEach(attribute -> {
            AttrName attrName = attribute.getKey();
            AttrName as = r.apply(attrName);
            if (as == null)
                as = attrName;
            renamed.put(as, attribute.getValue());
        });
        return new Tuple(renamed);
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
}
