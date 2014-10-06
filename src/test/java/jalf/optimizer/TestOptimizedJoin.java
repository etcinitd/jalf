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

    @Test
    public void testRestrict() {
        Relation suppliers = suppliers();
        Relation shipments = shipments();
        Relation operator = join(suppliers, shipments);

        Relation optimized = null;
        Relation expected = null;

        // when the restriction applies to left only
        optimized = optimized(operator)
                .restrict(eq(CITY, "London"));
        expected = suppliers
                .restrict(eq(CITY, "London"))
                .join(shipments);
        assertSameExpression(expected, optimized);

        // when the restriction applies to left and right
        optimized = optimized(operator)
                .restrict(eq(SID, "S1"));
        expected = suppliers
                .restrict(eq(SID, "S1"))
                .join(shipments.restrict(eq(SID, "S1")));
        assertSameExpression(expected, optimized);

        // when the restriction cannot be pushed down
        optimized = optimized(operator)
                .restrict(eq(STATUS, QTY));
        expected = suppliers
                .join(shipments)
                .restrict(eq(STATUS, QTY));
        assertSameExpression(expected, optimized);

        // when the restriction is a splittable AND
        optimized = optimized(operator)
                .restrict(eq(CITY, "LONDON").and(eq(QTY, 200)));
        expected = suppliers
                .restrict(eq(CITY, "LONDON"))
                .join(shipments.restrict(eq(QTY, 200)));
        assertSameExpression(expected, optimized);

        // when the restriction is a splittable NOT(OR)
        optimized = optimized(operator)
                .restrict(eq(CITY, "London").or(eq(QTY, 200)).not());
        expected = suppliers
                .restrict(eq(CITY, "London").not())
                .join(shipments.restrict(eq(QTY, 200).not()));
        assertSameExpression(expected, optimized);

        // when the restriction is a semi splittable NOT(OR)
        optimized = optimized(operator)
                .restrict(eq(CITY, "London").or(eq(QTY, STATUS)).not());
        expected = suppliers
                .restrict(eq(CITY, "London").not())
                .join(shipments)
                .restrict(eq(QTY, STATUS).not());
        assertSameExpression(expected, optimized);
    }

}
