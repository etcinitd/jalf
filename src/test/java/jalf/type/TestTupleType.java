package jalf.type;

import static org.junit.Assert.*;
import static jalf.fixtures.SuppliersAndParts.*;

import java.util.*;
import jalf.*;
import org.junit.Test;

public class TestTupleType {

    TupleType type = TupleType.varargs(SID, String.class);

    @Test
    public void testContains() {
        assertTrue(type.contains(Tuple.varargs(SID, "S1")));
        assertFalse(type.contains(Tuple.varargs()));
        assertFalse(type.contains(Tuple.varargs(SID, 12)));
        assertFalse(type.contains(Tuple.varargs(SID, "S1", STATUS, 12)));
        assertFalse(type.contains("hello"));
        assertFalse(type.contains(null));
    }

    @Test
    public void testToAttrList() {
        TupleType type = TupleType.varargs(STATUS, Integer.class, SID, String.class);
        AttrList expected = AttrList.attrs(STATUS, SID);
        assertEquals(expected, type.toAttrList());

        // it preserves the insertion order
        List<AttrName> list = Arrays.asList(STATUS, SID);
        assertEquals(list, expected.toList());
    }

    @Test
    public void testHashCode() {
        // it does not depend on the order
        TupleType t1 = TupleType.varargs(SID, String.class, STATUS, Integer.class);
        TupleType t2 = TupleType.varargs(STATUS, Integer.class, SID, String.class);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void testEquals() {
        // it is equal to itself
        assertTrue(type.equals(type));
        // it is equal to an equivalent
        assertTrue(type.equals(TupleType.varargs(SID, String.class)));
        // it is not equal to another ones
        assertFalse(type.equals(TupleType.varargs()));
        assertFalse(type.equals(TupleType.varargs(SID, String.class, STATUS, Integer.class)));
        assertFalse(type.equals(TupleType.varargs(SID, Integer.class)));
        assertFalse(type.equals(TupleType.varargs(NAME, String.class)));
    }

}
