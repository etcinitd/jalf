package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;

import jalf.*;
import org.junit.Test;

public class TestOptimizedJoin extends OptimizerTest {

    ///

    @Test
    public void testProject() {
        Relation suppliers = suppliers();
        Relation shipments = shipments();
        Relation operator = join(suppliers, shipments);

        Relation optimized = null;
        Relation expected = null;
        
        // when the three projections are needed
        optimized = optimized(operator).project(attrs(PID, NAME));
        expected = project(
                join(
                        project(suppliers, attrs(SID, NAME)),
                        project(shipments, attrs(SID, PID))),
                attrs(PID, NAME));
        assertSameExpression(expected, optimized);

        // when one of the internal projections is not needed
        optimized = optimized(operator).project(attrs(PID, QTY));
        expected = project(
                join(
                        project(suppliers, attrs(SID)), 
                        shipments),
                attrs(PID, QTY));
        assertSameExpression(expected, optimized);

        // when the external projection is not needed
        optimized = optimized(operator).project(attrs(SID, PID, CITY, QTY));
        expected = join(
                project(suppliers, attrs(SID, CITY)),
                shipments);
        assertSameExpression(expected, optimized);
    }

}
