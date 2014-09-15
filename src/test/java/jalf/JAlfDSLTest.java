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

        assertEquals(5, actual.count());
        assertEquals(expected, actual);
    }

}
