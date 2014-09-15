package jalf;

import org.junit.Test;

import static jalf.JAlfDSL.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.assertEquals;

/**
 * @author amirm
 */
public class JAlfDSLTest {
    // TODO rename, restrict, join

    @Test
    public void testEqualsAndHashCode() {
        Relation expected = suppliers();

        Relation actual = suppliers();

        assertEquals(expected, actual);
    }

    @Test
    public void testProject() {
        Relation expected = relation(
            tuple(SID, "S1", NAME, "Smith"),
            tuple(SID, "S2", NAME, "Jones"),
            tuple(SID, "S3", NAME, "Blake"),
            tuple(SID, "S4", NAME, "Clark"),
            tuple(SID, "S5", NAME, "Adams")
        );

        Relation actual = project(suppliers(), attrs(SID, NAME));

        assertEquals(expected, actual);
    }

    @Test
    public void testRename() {
        Relation expected = relation(
            tuple(SUPPLIER_ID, "S1", NAME, "Smith", STATUS, 20, LIVES_IN, "London"),
            tuple(SUPPLIER_ID, "S2", NAME, "Jones", STATUS, 10, LIVES_IN, "Paris"),
            tuple(SUPPLIER_ID, "S3", NAME, "Blake", STATUS, 30, LIVES_IN, "Paris"),
            tuple(SUPPLIER_ID, "S4", NAME, "Clark", STATUS, 20, LIVES_IN, "London"),
            tuple(SUPPLIER_ID, "S5", NAME, "Adams", STATUS, 30, LIVES_IN, "Athens")
        );

        Relation actual = rename(suppliers(), renaming(SID, SUPPLIER_ID, CITY, LIVES_IN));

        assertEquals(expected, actual);
    }

}
