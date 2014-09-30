package jalf.predicate;

import jalf.*;
import java.util.*;
import org.junit.Test;

import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class TestAmong {

    @Test
    public void testRename() {
        List<Object> values = Arrays.asList("S1", "S2");
        Among source = Predicate.among(SID, values);
        Renaming r = Renaming.extension(SID, SUPPLIER_ID);
        Predicate expected = Predicate.among(SUPPLIER_ID, values);
        Predicate got = source.rename(r);
        assertEquals(expected, got);
    }

    @Test
    public void testHashCode() {
        List<Object> values = Arrays.asList("S1", "S2");
        Among s1 = Predicate.among(SID, values);
        Among s2 = Predicate.among(SID, values);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    public void testEquals() {
        Among s1 = Predicate.among(SID, Arrays.asList("S1", "S2"));

        // it is equal to itself
        assertEquals(s1, s1);

        // it is equal to an equivalent
        Among s2 = Predicate.among(SID, Arrays.asList("S1", "S2"));
        assertEquals(s1, s2);

        // it is not equal to another one
        Among s3 = Predicate.among(SUPPLIER_ID, Arrays.asList("S1", "S2"));
        Among s4 = Predicate.among(SUPPLIER_ID, Arrays.asList("S1", "S3"));
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);

        // it is not equal to another one
        Predicate s5 = Predicate.eq(SID, "S1");
        assertNotEquals(s1, s5);
    }
}
