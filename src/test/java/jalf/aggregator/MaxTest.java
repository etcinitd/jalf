package jalf.aggregator;

import static jalf.DSL.heading;
import static jalf.DSL.max;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import jalf.Relation;

import org.junit.Test;

public class MaxTest {

    Relation r = relation(
            heading(SID, String.class, PID, String.class, QTY, Integer.class),
            tuple(SID, "S1", PID, "P1", QTY, 300),
            tuple(SID, "S1", PID, "P2", QTY, 200),
            tuple(SID, "S1", PID, "P3", QTY, 400),
            tuple(SID, "S1", PID, "P4", QTY, 200),
            tuple(SID, "S1", PID, "P5", QTY, 100),
            tuple(SID, "S1", PID, "P6", QTY, 100));

    @Test
    public void testItWorksAsExpectedbyMax() {
        Max m = max(QTY);
        r.stream().forEach(t -> m.accumulate(t));
        int expected = 400;
        assertEquals(expected, m.finish());
    }

    @Test
    public void testItWrongWorksbyMax() {
        Max m = max(QTY);
        r.stream().forEach(t -> m.accumulate(t));
        int expected = 500;
        assertNotEquals(expected, m.finish());
    }

}
