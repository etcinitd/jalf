package jalf.type;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestScalarType {

    ScalarType<Integer> intType = new ScalarType<>(Integer.class); 

    @Test
    public void testContains() {
        // it contains an instance of Integer
        assertTrue(intType.contains(12));

        // it does not contains non integers, especially null
        assertFalse(intType.contains("hello"));
        assertFalse(intType.contains(null));
    }

}
