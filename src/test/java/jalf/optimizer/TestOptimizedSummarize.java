package jalf.optimizer;

import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.avg;
import static jalf.DSL.count;
import static jalf.DSL.eq;
import static jalf.DSL.max;
import static jalf.DSL.project;
import static jalf.DSL.summarize;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import jalf.Relation;

import org.junit.Test;

public class TestOptimizedSummarize {

    private Relation rel = shipments();

    @Test
    public void testProject() {

        Relation operator = summarize(rel,attrs(SID),count(),attr("count"));
        Relation optimized = optimized(operator).
                project(attrs(SID));

        Relation expected = summarize(
                project(rel, attrs(SID)),
                attrs(SID),
                count(),
                attr("count"));

        assertSameExpression(expected, optimized);

        operator = summarize(rel,attrs(SID), avg(QTY),attr("avg"));
        optimized = optimized(operator).project(attrs(SID, QTY));

        expected = rel.
                project(attrs(SID, QTY)).
                summarize(attrs(SID), avg(QTY), attr("ag"));

        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrict(){
        Relation operator =  summarize(rel,attrs(SID), count(),attr("count"));;
        Relation optimized = optimized(operator).restrict(eq(SID, "S1"));

        Relation expected = rel
                .restrict(eq(SID, "S1"))
                .summarize(attrs(SID), count(), attr("count"));

        assertSameExpression(expected, optimized);

        operator =  summarize(rel,attrs(SID), max(QTY),attr("max"));;
        optimized = optimized(operator).restrict(eq(SID, "S1"));

        expected = rel
                .restrict(eq(SID, "S1"))
                .summarize(attrs(SID), count(), attr("max"));

        assertSameExpression(expected, optimized);
    }

}
