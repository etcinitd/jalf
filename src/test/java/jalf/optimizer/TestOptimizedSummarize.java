package jalf.optimizer;

import static jalf.DSL.attr;
import static jalf.DSL.attrs;
import static jalf.DSL.count;
import static jalf.DSL.eq;
import static jalf.DSL.max;
import static jalf.DSL.summarize;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;

import org.junit.Test;

import jalf.Relation;

public class TestOptimizedSummarize  extends OptimizerTest {

    private Relation rel = shipments();

    @Test
    public void testProjectWhenKeepingSummarizedAttribute() {
        // normal execution
        Relation operator = summarize(rel, attrs(SID, PID), count(), attr("count"));
        Relation optimized = optimized(operator)
                .project(attrs(SID, attr("count")));
        Relation expected =  operator
                .project(attrs(SID, attr("count")));
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testProjectWhenSummarizedAttributeIsThrownAway() {
        Relation operator = summarize(rel, attrs(SID, PID), count(), attr("count"));
        Relation optimized = optimized(operator).project(attrs(SID));
        Relation expected = rel.project(attrs(SID));
        assertSameExpression(expected, optimized);
    }

    // restrict optimization

    @Test
    public void testRestrictIsPushedIfPossible(){
        // restrict not on as
        Relation operator =  summarize(rel,attrs(SID, PID), count(), attr("count"));
        Relation optimized = optimized(operator)
                .restrict(eq(SID, "S1"));
        Relation expected = rel
                .restrict(eq(SID, "S1"))
                .summarize(attrs(SID, PID), count(), attr("count"));
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrictIsNotPushedWhenNotPossible(){
        // restrict  on as
        Relation operator = summarize(rel, attrs(SID), max(PID), attr("MAX_PID"));
        Relation optimized = optimized(operator)
                .restrict(eq(attr("MAX_PID"),"P6"));
        Relation expected = operator
                .restrict(eq(attr("MAX_PID"),"P6"));
        assertSameExpression(expected, optimized);
    }

}
