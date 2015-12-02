package jalf.dsl;

import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.DSL.union;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.TypeException;

import org.junit.Test;

public class UnionTest {

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
                tuple(SID, "S1"),
                tuple(SID, "S2"),
                tuple(SID, "S3")
                );
        Relation actual = union(left, right);

        // real test
        assertEquals(expected, actual);

        // equals() is complex and might be buggy when the input stream contains
        // duplicates (hence, is not a relation). So we count tuples as a second
        // check.
        assertEquals(3, actual.cardinality());
    }

    @Test(expected=TypeException.class)
    public void testHeaderIncompatile() {
        Relation left = relation(
                tuple(PID, "P1"),
                tuple(PID, "P2")
                );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3")
                );
        union(left, right);
    }

}
