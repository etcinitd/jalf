package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;
import jalf.*;
import jalf.relation.materialized.EmptyRelation;

import org.junit.Test;

public class TestOptimized extends OptimizerTest {

    @Test
    public void testProject() {
        Relation shipments = shipments();
        Relation operator = shipments;
        Relation optimized = optimized(operator).project(attrs(SID, PID, QTY));
        Relation expected = operator;
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrict() {
        Relation shipments = shipments();
        Relation operator = shipments;
        Relation optimized = null;
        Relation expected = null;

        // with TRUE
        optimized = optimized(operator).restrict(TRUE);
        expected = shipments;
        assertSameExpression(expected, optimized);

        // with FALSE
        optimized = optimized(operator).restrict(FALSE);
        assertTrue(optimized instanceof EmptyRelation);
    }

    @Test
    public void testJoin() {
        Relation shipments = shipments();
        Relation operator = shipments;
        Relation optimized = null;
        Relation expected = null;

        // with DUM
        optimized = optimized(operator).join(DUM);
        expected = DUM;
        assertSameExpression(expected, optimized);

        // with DEE
        optimized = optimized(operator).join(DEE);
        expected = operator;
        assertSameExpression(expected, optimized);
    }

}
