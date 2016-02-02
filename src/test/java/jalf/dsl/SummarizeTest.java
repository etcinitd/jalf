package jalf.dsl;
import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.count;
import static jalf.DSL.expression;
import static jalf.DSL.max;
import static jalf.DSL.relation;
import static jalf.DSL.summarize;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.WEIGHT;
import static jalf.fixtures.SuppliersAndParts.parts;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import jalf.Relation;

import org.junit.Test;
public class SummarizeTest {

    @Test
    public void testItWorksAsExpectedForCount() {

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

        Relation actual = summarize(shipments(),attrs(SID), count());

        // real test
        assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedForMax() {

        Relation expected = relation(
                tuple(PID, "P6", attr("MAX"), 19.0)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Relation actual = summarize(parts(),attrs(PID), max(expression(WEIGHT)));

        // real test
        assertEquals(expected, actual);

    }


}
