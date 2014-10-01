package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;

import org.junit.Test;

public class TestOptimizer extends OptimizerTest {

    @Test
    public void testItCascadesAsExpected() {
        // here we expect restrict to be pushed over project, leading to
        // two projections that merge together
        Relation suppliers = suppliers();
        Relation expression = suppliers
                .project(attrs(SID, NAME, CITY))
                .restrict(eq(SID, "S2"))
                .project(attrs(SID, NAME));
        Relation expected = suppliers
                .restrict(eq(SID, "S2"))
                .project(attrs(SID, NAME));
        Relation optimized = optimize(expression);
        assertProperlyOptimized(expression, expected, optimized);
    }

}
