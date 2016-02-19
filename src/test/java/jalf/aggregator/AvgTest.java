package jalf.aggregator;

import static jalf.DSL.avg;
import static jalf.DSL.heading;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import jalf.Relation;

import org.junit.Test;
;

public class AvgTest {

    Relation r = relation(
            heading(SID, String.class, PID, String.class, QTY, Integer.class),
            tuple(SID, "S1", PID, "P1", QTY, 300),
            tuple(SID, "S1", PID, "P2", QTY, 200),
            tuple(SID, "S1", PID, "P3", QTY, 400),
            tuple(SID, "S1", PID, "P4", QTY, 200),
            tuple(SID, "S1", PID, "P5", QTY, 100),
            tuple(SID, "S1", PID, "P6", QTY, 100));

    @Test
    public void testItWorksAsExpectedbyAvg() {
        Avg a = avg(QTY);
        r.stream().forEach(t -> a.accumulate(t));
        double expected = 216.6666666;
        assertEquals(expected, a.finish().doubleValue(), 0.001);
    }

    @Test
    public void testItWrongWorksAvg() {
        Avg a = avg(QTY);
        r.stream().forEach(t -> a.accumulate(t));
        double expected = 211.666666;
        assertNotEquals(expected, a.finish().doubleValue(), 0.001);
    }
}
