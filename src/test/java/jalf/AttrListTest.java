package jalf;

import org.junit.Test;

import static org.junit.Assert.*;
import static jalf.fixtures.SuppliersAndParts.*;

public class AttrListTest {

    @Test
    public void tesEquals() {
        AttrList s1 = AttrList.attrs(SID, NAME, STATUS);

        // it is equal to itself
        assertEquals(s1, s1);

        // it is equal to an equivalent
        assertEquals(s1, AttrList.attrs(SID, NAME, STATUS));
        assertEquals(s1, AttrList.attrs(NAME, SID, STATUS));
    }

    @Test
    public void tesHashCode() {
        AttrList s1 = AttrList.attrs(SID, NAME, STATUS);

        // it is equal to an equivalent
        assertEquals(s1.hashCode(), AttrList.attrs(SID, NAME, STATUS).hashCode());
        assertEquals(s1.hashCode(), AttrList.attrs(NAME, SID, STATUS).hashCode());
    }

    @Test
    public void testContains() {
        AttrList l = AttrList.attrs(SID, NAME, STATUS);

        assertTrue(l.contains(SID));
        assertFalse(l.contains(WEIGHT));
    }

    @Test
    public void testDifference() {
        AttrList s1 = AttrList.attrs(SID, NAME, STATUS);
        AttrList s2 = AttrList.attrs(CITY, NAME);
        AttrList expected = AttrList.attrs(SID, STATUS);
        assertEquals(expected, s1.difference(s2));
    }

    @Test
    public void testIntersect() {
        AttrList s1 = AttrList.attrs(SID, NAME, STATUS);
        AttrList s2 = AttrList.attrs(CITY, NAME);
        AttrList expected = AttrList.attrs(NAME);
        assertEquals(expected, s1.intersect(s2));
    }
}
