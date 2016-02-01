package jalf.dsl;
import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.relation;
import static jalf.DSL.summarize;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import jalf.Relation;
import jalf.aggregator.Aggregator;
import jalf.aggregator.Max;

public class SummarizeTest {

    @Test
    public void testItWorksAsExpectedForBY() {

        Relation expected = relation(
                tuple(SID, "S1", attr("TOTAL"), 1300),
                tuple(SID, "S2", attr("TOTAL"), 700),
                tuple(SID, "S3", attr("TOTAL"), 200),
                tuple(SID, "S4", attr("TOTAL"), 900)
                );

        // summarize takes :
        // a relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggreation),
        // an aggregation function (TODO)

        Aggregator monagregat= new Max(QTY,attr("TOTAL"));
        Relation actual = summarize(shipments(),attrs(SID), monagregat);

        // real test
        assertEquals(expected, actual);

    }




}
