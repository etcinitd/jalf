package jalf;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        // the order has no significance
        assertEquals(s1, AttrList.attrs(NAME, SID, STATUS));

        // it is not equal to projections/extensions/updates
        assertNotEquals(s1, AttrList.attrs(SID, NAME));
        assertNotEquals(s1, AttrList.attrs(SID, NAME, STATUS, CITY));
        assertNotEquals(s1, AttrList.attrs(SID, NAME, CITY));
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
    public void testStream() {
        // it keeps the original ordering of names
        AttrList l = AttrList.attrs(SID, NAME, STATUS);

        List<AttrName> got = l.stream().collect(Collectors.toList());
        List<AttrName> expected = Arrays.asList(SID, NAME, STATUS);
        assertEquals(expected, got);
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

    @Test
    public void testToList() {
        AttrList l = AttrList.attrs(SID, NAME, STATUS);
        List<AttrName> expected = Arrays.asList(SID, NAME, STATUS);
        assertEquals(expected, l.toList());
    }
}
