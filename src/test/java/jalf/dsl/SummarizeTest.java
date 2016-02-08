package jalf.dsl;
import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.avg;
import static jalf.DSL.count;
import static jalf.DSL.max;
import static jalf.DSL.relation;
import static jalf.DSL.summarize;
import static jalf.DSL.tuple;
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
                tuple(SID, "S1", attr("count"), 6),
                tuple(SID, "S2", attr("count"), 2),
                tuple(SID, "S3", attr("count"), 1),
                tuple(SID, "S4", attr("count"), 3)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Relation actual = summarize(shipments(),attrs(SID), count(),attr("count"));

        // real test
        assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedbyMax() {

        Relation expected = relation(
                tuple(SID, "S1", attr("MAX_QTY"), 400),
                tuple(SID, "S2", attr("MAX_QTY"), 400),
                tuple(SID, "S3", attr("MAX_QTY"), 200),
                tuple(SID, "S4", attr("MAX_QTY"),400)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Relation actual = summarize(shipments(),attrs(SID), max(QTY),attr("MAX_QTY"));

        // real test
        assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedbyAvg() {

        Relation expected = relation(
                tuple(SID, "S1", attr("AVG_QTY"), 216.66666666666666),
                tuple(SID, "S2", attr("AVG_QTY"), 350.0),
                tuple(SID, "S3", attr("AVG_QTY"), 200.0),
                tuple(SID, "S4", attr("AVG_QTY"),300.0)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Relation actual = summarize(shipments(),attrs(SID), avg(QTY),attr("AVG_QTY"));

        // real test
        assertEquals(expected, actual);

    }
}
