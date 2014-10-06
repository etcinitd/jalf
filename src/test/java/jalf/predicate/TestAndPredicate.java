package jalf.predicate;

import static org.junit.Assert.*;
import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;
import jalf.util.Pair;

import org.junit.Test;

public class TestAndPredicate {

    Predicate left = eq(CITY, "London");
    Predicate right = eq(STATUS, 200);
    Predicate anded = new And(left, right);

    @Test
    public void testTest() {
        Tuple t = tuple(CITY, "London", STATUS, 200);
        assertTrue(anded.test(t));

        t = tuple(CITY, "Paris", STATUS, 200);
        assertFalse(anded.test(t));
    }

    @Test
    public void testReferencedAttributes() {
        AttrList got = anded.getReferencedAttributes();
        AttrList expected = attrs(CITY, STATUS);
        assertEquals(expected, got);
    }

    @Test
    public void testRename() {
        Predicate got = anded.rename(renaming(CITY, LIVES_IN));
        Predicate expected = eq(LIVES_IN, "London").and(right);
        assertEquals(expected, got);
    }

    @Test
    public void testSplit() {
        Pair<Predicate> split;
        AttrList list;

        // with all attributes and more
        list = attrs(SID, CITY, STATUS, NAME);
        split = anded.split(list);
        assertEquals(anded, split.left);
        assertEquals(TRUE, split.right);

        // with only left attributes
        list = attrs(CITY);
        split = anded.split(list);
        assertEquals(left, split.left);
        assertEquals(right, split.right);

        // with no shared attribute
        list = attrs(QTY, PID);
        split = anded.split(list);
        assertEquals(TRUE, split.left);
        assertEquals(anded, split.right);
    }

    @Test
    public void testAnd() {
        // with a simple predicate
        Predicate furtherAnded = anded.and(eq(QTY, 200));
        assertTrue(furtherAnded instanceof And);
        assertEquals(3, ((And)furtherAnded).predicates.size());

        // with another AND
        furtherAnded = anded.and(eq(QTY, 200).and(eq(SID, "S1")));
        assertTrue(furtherAnded instanceof And);
        assertEquals(4, ((And)furtherAnded).predicates.size());
    }

    @Test
    public void testHashCode() {
        Predicate other = left.and(right);
        assertEquals(anded.hashCode(), other.hashCode());
    }

    @Test
    public void testEquals() {
        Predicate same = left.and(right);
        assertEquals(anded, same);

        Predicate notSame = eq(CITY, "Paris").and(right);
        assertNotEquals(anded, notSame);
    }
}
