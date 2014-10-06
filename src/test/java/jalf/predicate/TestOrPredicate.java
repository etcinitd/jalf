package jalf.predicate;

import static org.junit.Assert.*;
import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;
import jalf.util.Pair;

import org.junit.Test;

public class TestOrPredicate {

    Predicate left = eq(CITY, "London");
    Predicate right = eq(STATUS, 200);
    Predicate ored = new Or(left, right);

    @Test
    public void testTest() {
        Tuple t = tuple(CITY, "London", STATUS, 200);
        assertTrue(ored.test(t));

        t = tuple(CITY, "Paris", STATUS, 200);
        assertTrue(ored.test(t));

        t = tuple(CITY, "Paris", STATUS, 300);
        assertFalse(ored.test(t));
    }

    @Test
    public void testReferencedAttributes() {
        AttrList got = ored.getReferencedAttributes();
        AttrList expected = attrs(CITY, STATUS);
        assertEquals(expected, got);
    }

    @Test
    public void testRename() {
        Predicate got = ored.rename(renaming(CITY, LIVES_IN));
        Predicate expected = eq(LIVES_IN, "London").or(right);
        assertEquals(expected, got);
    }

    @Test
    public void testSplit() {
        AttrList list = null;
        Pair<Predicate> got;

        // when fully covered
        list = attrs(CITY, STATUS);
        got = ored.split(list);
        assertEquals(ored, got.left);
        assertEquals(TRUE, got.right);

        // when not full covered
        list = attrs(CITY);
        got = ored.split(list);
        assertEquals(TRUE, got.left);
        assertEquals(ored, got.right);
    }

    @Test
    public void testOr() {
        // with a simple predicate
        Predicate furtherOred = ored.or(eq(QTY, 200));
        assertTrue(furtherOred instanceof Or);
        assertEquals(3, ((Or)furtherOred).predicates.size());

        // with another OR
        furtherOred = ored.or(eq(QTY, 200).or(eq(SID, "S1")));
        assertTrue(furtherOred instanceof Or);
        assertEquals(4, ((Or)furtherOred).predicates.size());
    }

    @Test
    public void testHashCode() {
        Predicate other = left.or(right);
        assertEquals(ored.hashCode(), other.hashCode());
    }

    @Test
    public void testEquals() {
        Predicate same = left.or(right);
        assertEquals(ored, same);

        Predicate notSame = eq(CITY, "Paris").or(right);
        assertNotEquals(ored, notSame);
    }
}
