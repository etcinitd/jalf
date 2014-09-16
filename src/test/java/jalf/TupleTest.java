package jalf;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.assertEquals;

public class TupleTest {

    @Test
    public void testItProjectsAsExpected() {
        Tuple source = tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London");
        Tuple expected = tuple(SID, "S1", NAME, "Smith");
        Tuple actual = source.project(attrs(SID, NAME));
        assertEquals(expected, actual);
    }

    @Test
    public void testItRenamesAsExpected() {
        Tuple source = tuple(SID, "S1", NAME, "Smith");
        Tuple expected = tuple(SUPPLIER_ID, "S1", NAME, "Smith");
        Tuple actual = source.rename(renaming(SID, SUPPLIER_ID));
        assertEquals(expected, actual);
    }

}
