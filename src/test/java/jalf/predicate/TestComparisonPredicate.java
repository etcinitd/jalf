package jalf.predicate;

import jalf.*;
import jalf.util.Pair;

import org.junit.Test;

import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class TestComparisonPredicate {

    @Test
    public void testRename() {
        Renaming r = Renaming.extension(SID, SUPPLIER_ID);

        // it renames at left
        Predicate source = Predicate.eq(SID, "S1");
        Predicate expected = Predicate.eq(SUPPLIER_ID, "S1");
        Predicate got = source.rename(r);
        assertEquals(expected, got);

        // it renames at right, even if nothing to do
        source = Predicate.eq(SID, NAME);
        expected = Predicate.eq(SUPPLIER_ID, NAME);
        got = source.rename(r);
        assertEquals(expected, got);

        // it renames at right
        source = Predicate.eq(NAME, SID);
        expected = Predicate.eq(NAME, SUPPLIER_ID);
        got = source.rename(r);
        assertEquals(expected, got);
    }

    @Test
    public void testSplit() {
        Predicate source = null;
        AttrList list = null;
        Pair<Predicate> split = null;

        // when the source contains only one attribute...
        source = Predicate.eq(SID, "S1");

        // it splits correctly when list contains referenced attribute
        list = AttrList.attrs(SID, NAME);
        split = source.split(list);
        assertEquals(source, split.left);
        assertEquals(Predicate.TRUE, split.right);

        // it splits correctly when list does not
        list = AttrList.attrs(NAME, CITY);
        split = source.split(list);
        assertEquals(Predicate.TRUE, split.left);
        assertEquals(source, split.right);

        // when the source contains multiple attributes...
        source = Predicate.eq(SID, PID);

        // it splits correctly when list does contains all referenced attribute
        list = AttrList.attrs(SID, PID, NAME);
        split = source.split(list);
        assertEquals(source, split.left);
        assertEquals(Predicate.TRUE, split.right);

        // it splits correctly when list does not
        list = AttrList.attrs(SID, NAME);
        split = source.split(list);
        assertEquals(Predicate.TRUE, split.left);
        assertEquals(source, split.right);
    }

    @Test
    public void testHashCode() {
        Predicate e1 = Predicate.eq(SID, "S1");
        Predicate e2 = Predicate.eq(SID, "S1");
        assertEquals(e1.hashCode(), e2.hashCode());
    }

    @Test
    public void testEquals() {
        Predicate e1 = Predicate.eq(SID, "S1");

        // it is equal to itself
        assertEquals(e1, e1);

        // it is equal to an equivalent
        Predicate e2 = Predicate.eq(SID, "S1");
        assertEquals(e1, e2);

        // it is not equal to another eq
        Predicate e3 = Predicate.eq(SID, "S3");
        assertNotEquals(e1, e3);

        // it is not equal to a neq
        Predicate e4 = Predicate.neq(SID, "S3");
        assertNotEquals(e1, e4);
    }
}
