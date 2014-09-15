package jalf;

import java.util.HashMap;
import java.util.Map;

import static jalf.util.ValidationUtils.*;

/**
 * @author amirm
 */
public class Tuple {
    private Map<AttrName, AttrValue> attrs;

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
            attrs.put(AttrName.attr(keyStr), new AttrValue(value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple tuple = (Tuple) o;

        if (!attrs.equals(tuple.attrs)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return attrs.hashCode();
    }
}
