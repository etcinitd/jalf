package jalf.test.dsl;

import jalf.*;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class ProjectTest {

    @Test
    public void testItWorksAsExpected() {
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
    public void testItThrowsWhenNoSuchAttributeName() {
        try {
            project(suppliers(), attrs(SID, WEIGHT));
            assertFalse(true);
        } catch (TypeException ex) {
            assertEquals("No such attributes: weight", ex.getMessage());
        }
    }
}
