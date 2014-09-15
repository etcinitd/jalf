package jalf;

import static jalf.util.ValidationUtils.validate;
import static jalf.util.ValidationUtils.validateCast;
import static jalf.util.ValidationUtils.validateNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * @author amirm
 */
public class Tuple {
    private Map<AttrName, Object> attrs;

    public Tuple(Map<AttrName, Object> attrs){
        this.attrs = attrs;
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
        for (AttrName attrName: on){
            p.put(attrName, attrs.get(attrName));
        }
        return new Tuple(p);
    }

    public Tuple rename(UnaryOperator<AttrName> r) {
        Map<AttrName, Object> renamed = new HashMap<>();
        for (AttrName attrName: attrs.keySet()) {
            AttrName as = r.apply(attrName);
            if (as == null)
                as = attrName;
            renamed.put(as, attrs.get(attrName));
        }
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
