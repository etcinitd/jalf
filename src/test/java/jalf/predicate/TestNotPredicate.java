package jalf.predicate;

import static org.junit.Assert.*;
import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;
import jalf.util.Pair;

import org.junit.Test;

public class TestNotPredicate {

    Predicate inner = eq(CITY, "London");
    Predicate not = new Not(inner);

    @Test
    public void testTest() {
        Tuple t;

        t = tuple(CITY, "Paris", STATUS, 20);
        assertTrue(not.test(t));

        t = tuple(CITY, "London", STATUS, 20);
        assertFalse(not.test(t));
    }

    @Test
    public void testReferencedAttributes() {
        AttrList got = not.getReferencedAttributes();
        AttrList expected = attrs(CITY);
        assertEquals(expected, got);
    }

    @Test
    public void testRename() {
        Predicate got = not.rename(renaming(CITY, LIVES_IN));
        Predicate expected = new Not(eq(LIVES_IN, "London"));
        assertEquals(expected, got);
    }

    @Test
    public void testSplit() {
        AttrList list;
        Pair<Predicate> got;

        // when fully covered
        not = new Not(eq(CITY, "London"));
        list = attrs(CITY);
        got = not.split(list);
        assertEquals(not, got.left);
        assertEquals(TRUE, got.right);

        // when not covered at all
        not = new Not(eq(CITY, "London"));
        list = attrs(STATUS);
        got = not.split(list);
        assertEquals(TRUE, got.left);
        assertEquals(not, got.right);

        // on an optimizable AND
        Predicate left = eq(CITY, "London");
        Predicate right = eq(STATUS, 200);
        not = new Not(left.and(right));
        list = attrs(CITY, STATUS);
        got = not.split(list);
        assertEquals(not, got.left);
        assertEquals(TRUE, got.right);

        // on an unoptimizable AND
        list = attrs(CITY, NAME);
        got = not.split(list);
        assertEquals(TRUE, got.left);
        assertEquals(not, got.right);

        // on a fully covered OR
        not = new Not(left.or(right));
        list = attrs(CITY, STATUS);
        got = not.split(list);
        assertEquals(not, got.left);
        assertEquals(TRUE, got.right);

        // on an optimizable, not fully covered OR
        // not(city = 'London' or status = 200)
        // not(city = 'London') and not(status = 200)
        // left may be pushed down, right may not
        not = new Not(left.or(right));
        list = attrs(CITY, QTY);
        got = not.split(list);
        assertEquals(new Not(left), got.left);
        assertEquals(new Not(right), got.right);
    }

    @Test
    public void testHashCode() {
        Predicate other = new Not(inner);
        assertEquals(not.hashCode(), other.hashCode());
    }

    @Test
    public void testEquals() {
        Predicate same = new Not(inner);
        assertEquals(not, same);

        Predicate notSame = new Not(eq(CITY, "Paris"));
        assertNotEquals(not, notSame);
    }
}
