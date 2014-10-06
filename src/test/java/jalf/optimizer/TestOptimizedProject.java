package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;

import jalf.*;
import org.junit.Test;

public class TestOptimizedProject extends OptimizerTest {

    ///

    @Test
    public void testProject() {
        Relation suppliers = suppliers();
        Relation operator = project(suppliers, attrs(SID, NAME, CITY));
        Relation optimized = optimized(operator).project(attrs(SID, CITY));
        Relation expected = project(suppliers, attrs(SID, CITY));
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrict() {
        Relation suppliers = suppliers();
        Predicate pred = eq(CITY, "London");
        AttrList attrs = attrs(SID, NAME, CITY);
        Relation operator = project(suppliers, attrs);
        Relation optimized = optimized(operator).restrict(pred);
        Relation expected = project(restrict(suppliers, pred), attrs);
        assertSameExpression(expected, optimized);
    }

}
