package jalf.test.dsl;

import jalf.*;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class RestrictTest {

    @Test
    public void testItSupportsSimpleEqualityRestrict() {
        Relation expected = relation(
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London")
        );
        Relation actual = restrict(suppliers(), eq(CITY, "London"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTestingEqualityOfAttributes() {
        Relation source = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation expected = relation(
                tuple(STATUS, 20, QTY, 20)
        );
        Relation actual = restrict(source, eq(STATUS, QTY));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsNativePredicates() {
        Relation expected = relation(
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
        Relation actual = restrict(suppliers(),
                t -> ((String)t.get(NAME)).indexOf('a') >= 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsFriendlyNativePredicates() {
        Relation expected = relation(
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
        Relation actual = restrict(suppliers(), NAME,
                name -> name.indexOf('a') >= 0);
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTwoTypeCastsForNativePredicates() {
        Relation expected = relation(
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
        Relation actual = restrict(suppliers(), STATUS.asInt(),
                status -> status == 30);
        assertEquals(expected, actual);
    }
}
