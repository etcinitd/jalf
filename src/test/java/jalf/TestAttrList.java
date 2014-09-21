package jalf;

import org.junit.Test;
import static org.junit.Assert.*;
import static jalf.test.fixtures.SuppliersAndParts.*;

public class TestAttrList {

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
    public void testDifference() {
        AttrList s1 = AttrList.attrs(SID, NAME, STATUS);
        AttrList s2 = AttrList.attrs(CITY, NAME);
        AttrList expected = AttrList.attrs(SID, STATUS);
        assertEquals(expected, s1.difference(s2));
    }

}
