package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

import java.util.List;

import jalf.*;
import jalf.util.ToAstVisitor;

import org.junit.Test;

public class TestOptimizedProject {

    private Optimizer optimizer;

    public TestOptimizedProject() {
        this.optimizer = new Optimizer();
    }

    private Relation optimized(Relation operator) {
        return optimizer.optimized(operator);
    }

    private void assertSameExpression(Relation expected, Relation optimized) {
        List<Object> refAst = expected.accept(new ToAstVisitor());
        List<Object> optAst = optimized.accept(new ToAstVisitor());
        assertEquals(refAst, optAst);
    }

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
