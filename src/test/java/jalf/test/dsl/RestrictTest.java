package jalf.test.dsl;

import jalf.Relation;
import org.junit.Test;

import static jalf.DSL.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

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
    public void testItSupportsSimpleNotEqualityRestrict() {
        Relation expected = relation(
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
        Relation actual = restrict(suppliers(), neq(CITY, "London"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTestingNotEqualityOfAttributes() {
        Relation source = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation expected = relation(
                tuple(STATUS, 20, QTY, 10)
        );
        Relation actual = restrict(source, neq(STATUS, QTY));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsSimpleGtRestrict() {
        Relation expected = relation(
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
        Relation actual = restrict(suppliers(), gt(STATUS, 20));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTestingGtOfAttributes() {
        Relation source = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation expected = relation(
                tuple(STATUS, 20, QTY, 10)
        );
        Relation actual = restrict(source, gt(STATUS, QTY));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsSimpleGteRestrict() {
        Relation expected = relation(
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
        Relation actual = restrict(suppliers(), gte(STATUS, 20));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTestingGteOfAttributes() {
        Relation source = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20),
                tuple(STATUS, 10, QTY, 20)
        );
        Relation expected = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation actual = restrict(source, gte(STATUS, QTY));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsSimpleLtRestrict() {
        Relation expected = relation(
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris")
        );
        Relation actual = restrict(suppliers(), lt(STATUS, 20));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTestingLtOfAttributes() {
        Relation source = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation expected = relation(
                tuple(STATUS, 20, QTY, 10)
        );
        Relation actual = restrict(source, lt(QTY, STATUS));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsSimpleLteRestrict() {
        Relation expected = relation(
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London")
        );
        Relation actual = restrict(suppliers(), lte(STATUS, 20));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsTestingLteOfAttributes() {
        Relation source = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation expected = relation(
                tuple(STATUS, 20, QTY, 10),
                tuple(STATUS, 20, QTY, 20)
        );
        Relation actual = restrict(source, lte(QTY, STATUS));
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
    public void testItSupportsAttributeAmongIterable() {
        Relation expected = relation(
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London")
        );

        Relation actual = restrict(suppliers(), among(CITY, asList("London", "Paris")));

        assertEquals(expected, actual);
    }
}
