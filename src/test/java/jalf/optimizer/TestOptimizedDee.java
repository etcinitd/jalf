package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;

import org.junit.Test;

public class TestOptimizedDee extends OptimizerTest {

    @Test
    public void testProject() {
        Relation operator = Relation.DEE;
        Relation optimized = optimized(operator).project(attrs(SID));
        Relation expected = Relation.DEE;
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRename() {
        Relation operator = Relation.DEE;
        Relation optimized = optimized(operator).rename(renaming(SID, SUPPLIER_ID));
        Relation expected = Relation.DEE;
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testJoin() {
        Relation suppliers = suppliers();
        Relation operator = Relation.DEE;
        Relation optimized = optimized(operator).join(suppliers);
        Relation expected = suppliers;
        assertSameExpression(expected, optimized);
    }

}
