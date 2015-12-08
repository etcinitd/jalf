package jalf.dsl;

import static jalf.DSL.minus;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.TypeException;

import org.junit.Test;


public class MinusTest {

    @Test
    public void testItWorksAsExpected(){
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2"),
                tuple(SID, "S3"),
                tuple(SID, "S4")
                );
        Relation right = relation(
                tuple(SID, "S2"),
                tuple(SID, "S3"),
                tuple(SID, "S5")
                );
        Relation expected = relation(
                tuple(SID, "S1"),
                tuple(SID, "S4")
                );
        Relation actual = minus(left, right);

        // real test
        assertEquals(expected, actual);

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
        minus(left, right);

    }

}
