package jalf.type;

import static org.junit.Assert.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import jalf.*;
import static jalf.DSL.*;

import org.junit.Test;

public class TestRelationType {

    RelationType type = RelationType.varargs(SID, String.class);

    @Test
    public void testEquals() {
        // it is equal to itself
        assertTrue(type.equals(type));
        // it is equal to an equivalent
        assertTrue(type.equals(RelationType.varargs(SID, String.class)));
        // it is not equal to another ones
        assertFalse(type.equals(RelationType.varargs()));
        assertFalse(type.equals(RelationType.varargs(SID, String.class, STATUS, Integer.class)));
        assertFalse(type.equals(RelationType.varargs(SID, Integer.class)));
        assertFalse(type.equals(RelationType.varargs(NAME, String.class)));
    }

    @Test
    public void testContains() {
        // it contains a proper relation
        Relation r = relation(tuple(SID, "S1"));
        assertTrue(type.contains(r));

        // it does not contain a non relation
        assertFalse(type.contains(null));
        assertFalse(type.contains(12));
        assertFalse(type.contains(Tuple.varargs(SID, 12)));

        // it does not contain a relation of another type
        r = relation(heading());
        assertFalse(type.contains(r));
        r = relation(tuple(SID, 12));
        assertFalse(type.contains(r));
        r = relation(tuple(SID, "S1", STATUS, 12));
        assertFalse(type.contains(r));
    }

}
