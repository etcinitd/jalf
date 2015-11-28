package jalf.dsl;

import static jalf.DSL.heading;
import static jalf.DSL.intersect;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.type.Heading;

import org.junit.Test;

public class IntersectTest {
    @Test
    public void testItWorksAsExpected() {
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2")
                );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3")
                );
        Relation expected = relation(
                tuple(SID, "S2")
                );
        Relation actual = intersect(left, right);
        assertEquals(expected, actual);
    }

    @Test
    public void testItWorksNoCommonTuples() {
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2")
                );
        Relation right = relation(
                tuple(SID, "S4"),
                tuple(SID, "S3")
                );
        Heading head = heading(SID, String.class);
        Relation expected = relation(head);

        Relation actual = intersect(left, right);
        assertEquals(expected, actual);
    }

}
