package jalf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author amirm
 */
public class Tuple {
    private Map<Object, Object> pairs;

    public Tuple(Object... keyValuePairs) {
        validate("Length of pairs must be even.", keyValuePairs.length % 2, 0);
        if (keyValuePairs.length == 0)
            return;

        pairs = new HashMap<>();
        for (int i = 0; i < keyValuePairs.length; i++) {
            Object key = keyValuePairs[i++];
            Object value = keyValuePairs[i];
            pairs.put(key, value);
        }
    }

    private void validate(String msg, int size, int expected) {
        if (size != expected)
            throw new IllegalArgumentException(msg);
    }
}
