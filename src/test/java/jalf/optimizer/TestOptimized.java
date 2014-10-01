package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;

import jalf.*;
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

}
