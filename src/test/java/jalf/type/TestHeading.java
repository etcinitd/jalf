package jalf.type;

import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class TestHeading {

    Heading h = Heading.varargs(SID, String.class);

    @Test
    public void testEquals() {
        // it is equal to itself
        assertTrue(h.equals(h));
        // it is equal to an equivalent
        assertTrue(h.equals(Heading.varargs(SID, String.class)));
        // it is not equal to another ones
        assertFalse(h.equals(Heading.varargs()));
        assertFalse(h.equals(Heading.varargs(SID, String.class, STATUS, Integer.class)));
        assertFalse(h.equals(Heading.varargs(SID, Integer.class)));
        assertFalse(h.equals(Heading.varargs(NAME, String.class)));
    }

}
