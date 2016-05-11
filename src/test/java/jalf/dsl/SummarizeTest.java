package jalf.dsl;
import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.avg;
import static jalf.DSL.count;
import static jalf.DSL.heading;
import static jalf.DSL.max;
import static jalf.DSL.relation;
import static jalf.DSL.summarize;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.Relation;
public class SummarizeTest {

    @Test
    public void testItWorksAsExpectedbyCount() {
        Relation expected = relation(
                tuple(SID, "S1", attr("count"), 6L),
                tuple(SID, "S2", attr("count"), 2L),
                tuple(SID, "S3", attr("count"), 1L),
                tuple(SID, "S4", attr("count"), 3L)
                );
        Relation actual = summarize(shipments(), attrs(SID), count(), attr("count"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItWorksAsExpectedbyMax() {
        Relation expected = relation(
                tuple(SID, "S1", attr("MAX_PID"), "P6"),
                tuple(SID, "S2", attr("MAX_PID"), "P2"),
                tuple(SID, "S3", attr("MAX_PID"), "P2"),
                tuple(SID, "S4", attr("MAX_PID"), "P5")
                );
        Relation actual = summarize(shipments(),attrs(SID), max(PID),attr("MAX_PID"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItWorksAsExpectedbyAvg() {
        Relation expected = relation(
                tuple(SID, "S1", attr("AVG_QTY"), 216.66666666666666),
                tuple(SID, "S2", attr("AVG_QTY"), 350.0),
                tuple(SID, "S3", attr("AVG_QTY"), 200.0),
                tuple(SID, "S4", attr("AVG_QTY"), 300.0)
                );
        Relation actual = summarize(shipments(),attrs(SID), avg(QTY),attr("AVG_QTY"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItWorksAsExpectedbyAvgWithMultipleBy() {
        Relation expected = relation(
                heading(SID, String.class, PID, String.class, attr("AVG_QTY"), Double.class),
                tuple(SID, "S1", PID, "P1", attr("AVG_QTY"), 300.0),
                tuple(SID, "S1", PID, "P2", attr("AVG_QTY"), 200.0),
                tuple(SID, "S1", PID, "P3", attr("AVG_QTY"), 400.0),
                tuple(SID, "S1", PID, "P4", attr("AVG_QTY"), 200.0),
                tuple(SID, "S1", PID, "P5", attr("AVG_QTY"), 100.0),
                tuple(SID, "S1", PID, "P6", attr("AVG_QTY"), 100.0),
                tuple(SID, "S2", PID, "P1", attr("AVG_QTY"), 300.0),
                tuple(SID, "S2", PID, "P2", attr("AVG_QTY"), 400.0),
                tuple(SID, "S3", PID, "P2", attr("AVG_QTY"), 200.0),
                tuple(SID, "S4", PID, "P2", attr("AVG_QTY"), 200.0),
                tuple(SID, "S4", PID, "P4", attr("AVG_QTY"), 300.0),
                tuple(SID, "S4", PID, "P5", attr("AVG_QTY"), 400.0)
                );
        Relation actual = summarize(shipments(),attrs(SID, PID), avg(QTY),attr("AVG_QTY"));
        assertEquals(expected, actual);
    }

}
