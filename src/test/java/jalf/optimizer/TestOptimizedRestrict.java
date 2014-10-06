package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;

import org.junit.Test;

public class TestOptimizedRestrict extends OptimizerTest {

    @Test
    public void testRestrict() {
        Relation suppliers = suppliers();

        Predicate inner = eq(CITY, "London");
        Predicate outer = eq(STATUS, 200);
        
        // restrict(
        //   restrict(suppliers, city == 'London'),
        //   status = 200)
        Relation operator = restrict(suppliers, inner);
        Relation optimized = optimized(operator).restrict(outer);

        // restrict(suppliers, ... AND ...)
        Relation expected = restrict(suppliers, inner.and(outer));
        assertSameExpression(expected, optimized);
    }

}
