package jalf.aggregator;

import static jalf.DSL.avg;
import static jalf.DSL.heading;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.COLOR;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.WEIGHT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import jalf.Relation;
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

    Relation rdouble = relation(
            heading(PID, String.class, NAME, String.class, COLOR, String.class, WEIGHT, Double.class, CITY, String.class),
            tuple(PID, "P1", NAME, "Nut",   COLOR, "Red",   WEIGHT, 12.0, CITY, "London"),
            tuple(PID, "P2", NAME, "Bolt",  COLOR, "Green", WEIGHT, 17.0, CITY, "Paris"),
            tuple(PID, "P3", NAME, "Screw", COLOR, "Blue",  WEIGHT, 17.0, CITY, "Oslo"),
            tuple(PID, "P4", NAME, "Screw", COLOR, "Red",   WEIGHT, 14.0, CITY, "London"),
            tuple(PID, "P5", NAME, "Cam",   COLOR, "Blue",  WEIGHT, 12.0, CITY, "Paris"),
            tuple(PID, "P6", NAME, "Cog",   COLOR, "Red",   WEIGHT, 19.0, CITY, "London"));
    @Test
    public void testItWorksAsExpectedbyAvgInteger() {
        Avg a = avg(QTY);
        r.stream().forEach(t -> a.accumulate(t));
        double expected = 216.6666666;
        assertEquals(expected, a.finish().doubleValue(), 0.001);
    }

    @Test
    public void testItWrongWorksAvgInteger() {
        Avg a = avg(QTY);
        r.stream().forEach(t -> a.accumulate(t));
        double expected = 211.666666;
        assertNotEquals(expected, a.finish().doubleValue(), 0.001);
    }


    @Test
    public void testItWorksAsExpectedbyAvgDouble() {
        Avg a = avg(WEIGHT);
        rdouble.stream().forEach(t -> a.accumulate(t));
        double expected = 15.166666667;
        assertEquals(expected, a.finish().doubleValue(), 0.001);
    }

}
