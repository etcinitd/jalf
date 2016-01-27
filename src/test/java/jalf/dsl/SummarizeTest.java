package jalf.dsl;

import static jalf.DSL.attr;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static jalf.fixtures.SuppliersAndParts.suppliers;
import static org.junit.Assert.assertEquals;
import jalf.Relation;

import org.junit.Test;

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
        Relation actual = summarize(shipments(), SID, attr("TOTAL"), Max(QTY));

        // real test
        assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedForPER() {

        Relation expected = relation(
                tuple(SID, "S1", attr("TOTAL"), 1300),
                tuple(SID, "S2", attr("TOTAL"), 700),
                tuple(SID, "S3", attr("TOTAL"), 200),
                tuple(SID, "S4", attr("TOTAL"), 900),
                tuple(SID, "S5", attr("TOTAL"), 0)
                );

        // summarize takes :
        // the 1er relation,
        // the 2nd relation,
        // an attribut name (for grouping),
        // an attribut name (for result of aggregation),
        // an aggregation function (TODO)
        Relation actual = summarize(shipments(), suppliers(), SID, attr("TOTAL"), Count());

        // real test
        assertEquals(expected, actual);


    }
}
