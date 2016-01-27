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

import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;

import jalf.Relation;
import jalf.Type;
import jalf.TypeException;

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

    Relation rA = relation(
            heading(COLOR, String.class, PID, String.class, QTY, A.class),
            tuple(COLOR, "S1", PID, "P1", QTY, new A(300)),
            tuple(COLOR, "S1", PID, "P2", QTY, new A(200)),
            tuple(COLOR, "S1", PID, "P3", QTY, new A(400)),
            tuple(COLOR, "S1", PID, "P4", QTY, new A(200)),
            tuple(COLOR, "S1", PID, "P5", QTY, new A(100)),
            tuple(COLOR, "S1", PID, "P6", QTY, new A(100)));

    @Test
    public void testItWorksAsExpectedbyAvgInteger() {
        Avg a = avg(QTY);
        r.stream().forEach(t -> a.accumulate(t));
        double expected = 216.6666666;
        assertEquals(expected, a.finish().doubleValue(), 0.001);
    }

    @Test
    public void testItWorksAsExpectedbyAvgDouble() {
        Avg a = avg(WEIGHT);
        rdouble.stream().forEach(t -> a.accumulate(t));
        double expected = 15.166666667;
        assertEquals(expected, a.finish().doubleValue(), 0.001);
    }

    @Test
    public void testgetResultingType1(){
        Avg a = avg(QTY);
        Type<?> t = a.getResultingType(r.getType());
        assertEquals(t, Type.scalarType(Double.class));
    }

    @Test(expected=TypeException.class)
    public void testgetResultingType2(){
        Avg a = avg(SID);
        a.getResultingType(r.getType());
    }

    @Test
    public void testgetResultingType3(){
        Avg a = avg(QTY);
        Type<?> t = a.getResultingType(rA.getType());
        assertEquals(t, Type.scalarType(Double.class));
    }

    /**
     * class used to test the case where the type of as not directly extends Number but
     * a subclass of Number
     */
    static class A extends AtomicLong{
        private static final long serialVersionUID = 1L;

        public A(long a){
            super(a);
        }

    }

}
