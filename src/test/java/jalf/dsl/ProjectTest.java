package jalf.dsl;

import static jalf.DSL.attrs;
import static jalf.DSL.heading;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.project;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.STATUS;
import static jalf.fixtures.SuppliersAndParts.WEIGHT;
import static jalf.fixtures.SuppliersAndParts.suppliers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import jalf.Relation;
import jalf.TypeException;

import org.junit.Test;

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
    public void testProjectonKey() {
        Relation expected = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2"),
                tuple(SID, "S3"),
                tuple(SID, "S4"),
                tuple(SID, "S5")
                );
        Relation  projected =relation(
                heading(SID, String.class, NAME, String.class, STATUS, Integer.class, CITY, String.class),
                keys(key(SID)),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
                );
        Relation actual = project(projected, attrs(SID));
        assertEquals(expected, actual);
    }

    @Test
    public void testProjectOnKey2() {
        Relation expected = relation(
                tuple(SID, "S1"),
                tuple(SID, "S3"),
                tuple(SID, "S4"),
                tuple(SID, "S5")
                );
        Relation  projected =relation(
                heading(SID, String.class, NAME, String.class, STATUS, Integer.class, CITY, String.class),
                keys(key(SID)),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S1", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S1", NAME, "James", STATUS, 10, CITY, "L.A."),
                tuple(SID, "S1", NAME, "Bond", STATUS, 40, CITY, "New Yord"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
                );
        Relation actual = project(projected, attrs(SID));
        assertEquals(expected, actual);
    }

    @Test
    public void testProjectOnKey3() {
        Relation expected = relation(
                tuple(CITY, "London"),
                tuple(CITY, "Paris"),
                tuple(CITY, "L.A."),
                tuple(CITY, "New Yord")
                );
        Relation  projected =relation(
                heading(SID, String.class, NAME, String.class, STATUS, Integer.class, CITY, String.class),
                keys(key(SID)),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S1", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S1", NAME, "James", STATUS, 10, CITY, "L.A."),
                tuple(SID, "S1", NAME, "Bond", STATUS, 40, CITY, "New Yord"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London")
                );
        Relation actual = project(projected, attrs(CITY));
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
