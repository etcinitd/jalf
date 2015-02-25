package jalf.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CollectionUtilsTest {

    @Test
    public void testMapOfOnKeysAndValues() {
        String[] keys = new String[]{};
        Integer[] values = new Integer[]{};
        assertEquals(new HashMap<>(), CollectionUtils.mapOf(keys, values));

        keys = new String[]{ "a", "b" };
        values = new Integer[]{ 1, 2 };
        Map<String, Integer> expected = new HashMap<>();
        expected.put("a", 1);
        expected.put("b", 2);
        Map<String, Integer> got = CollectionUtils.mapOf(keys, values);
        assertEquals(expected, got);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMapOfOnKeysAndValuesWhenIllegal() {
        String[] keys = new String[]{ "a", "b" };
        Integer[] values = new Integer[]{ 1, 2, 3 };
        CollectionUtils.mapOf(keys, values);
    }

}
