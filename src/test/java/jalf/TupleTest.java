package jalf;

import jalf.type.TupleType;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class TupleTest {

    @Test
    public void testGet() {
        Tuple t1 = tuple(SID, "S1", NAME, "Smith");
        assertEquals("S1", t1.get(SID));
        assertNull(t1.get(CITY));
    }

    @Test
    public void testFetch() {
        Tuple t1 = tuple(SID, "S1", NAME, "Smith", CITY, "London");
        List<Object> expected = Arrays.asList("S1", "London");
        assertEquals(expected, t1.fetch(attrs(SID, CITY)));
    }

    @Test
    public void testProject() {
        Tuple source = tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London");
        Tuple expected = tuple(SID, "S1", NAME, "Smith");

        TupleType type = TupleType.varargs(SID, String.class, NAME, String.class);
        Tuple actual = source.project(attrs(SID, NAME), type);
        assertEquals(expected, actual);

        // check the post condition too
        assertTrue(type.contains(actual));
    }

    @Test
    public void testRename() {
        Tuple source = tuple(SID, "S1", NAME, "Smith");
        Tuple expected = tuple(SUPPLIER_ID, "S1", NAME, "Smith");

        TupleType type = TupleType.varargs(SUPPLIER_ID, String.class, NAME, String.class);
        Tuple actual = source.rename(renaming(SID, SUPPLIER_ID), type);
        assertEquals(expected, actual);

        // check the post condition too
        assertTrue(type.contains(actual));
    }

    @Test
    public void testJoin() {
        Tuple left = tuple(SID, "S1", NAME, "Smith");
        Tuple right = tuple(SID, "S1", CITY, "London");
        Tuple expected = tuple(SID, "S1", NAME, "Smith", CITY, "London");

        TupleType type = TupleType.varargs(SID, String.class, NAME,
                String.class, CITY, String.class);
        Tuple actual = left.join(right, type);
        assertEquals(expected, actual);

        // check the post condition too
        assertTrue(type.contains(actual));
    }

    /*
     * This test violates the precondition of Tuple#join on intent. It just
     * checks that POLS applies in this case.
     */
    @Test
    public void testJoinFavorsRightTuple() {
        Tuple left = tuple(SID, "S1", NAME, "Smith");
        Tuple right = tuple(SID, "S1", NAME, "Jones", CITY, "London");

        TupleType type = TupleType.varargs(SID, String.class, NAME,
                String.class, CITY, String.class);
        Tuple actual = left.join(right, type);

        assertEquals(actual.get(NAME), "Jones");
    }

    @Test
    public void testEquals(){
        Tuple t1 = tuple(SID, "S1", NAME, "Smith");
        Tuple t2 = null;

        // it is equal to itself and an equivalent
        t2 = tuple(SID, "S1", NAME, "Smith");
        assertEquals(t1, t1);
        assertEquals(t1, t2);

        // the order is not significant
        t2 = tuple(NAME, "Smith", SID, "S1");
        assertEquals(t1, t2);

        // it is not equal to a projection
        t2 = tuple(SID, "S1");
        assertNotEquals(t1, t2);

        // it is not equal to an extension
        t2 = tuple(SID, "S1", CITY, "London");
        assertNotEquals(t1, t2);

        // it is not equal to an update
        t2 = tuple(SID, "S1", NAME, "Jones");
        assertNotEquals(t1, t2);

        // TODO: (inheritance) add tests that take the type into account
    }

    @Test
    public void testHashCode() {
        Tuple t1 = tuple(SID, "S1", NAME, "Smith");
        Tuple t2 = tuple(SID, "S1", NAME, "Smith");
        assertEquals(t1.hashCode(), t2.hashCode());
    }

}
