package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;

import org.junit.Test;

public class TestOptimizedDum extends OptimizerTest {

    @Test
    public void testProject() {
        Relation operator = Relation.DUM;
        Relation optimized = optimized(operator).project(attrs(SID));
        Relation expected = Relation.DUM;
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRename() {
        Relation operator = Relation.DUM;
        Relation optimized = optimized(operator).rename(renaming(SID, SUPPLIER_ID));
        Relation expected = Relation.DUM;
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrict() {
        Relation operator = Relation.DUM;
        Relation optimized = optimized(operator).restrict(eq(SID, "S1"));
        Relation expected = Relation.DUM;
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testJoin() {
        Relation operator = Relation.DUM;
        Relation optimized = optimized(operator).join(suppliers());
        Relation expected = Relation.DUM;
        assertSameExpression(expected, optimized);
    }

}
