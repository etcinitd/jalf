package jalf.optimizer;

import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.util.ToAstVisitor;

import java.util.List;

public class OptimizerTest {

    private Optimizer optimizer;

    public OptimizerTest() {
        this.optimizer = new Optimizer();
    }

    /** Requests the optimizer to decorate `operator` as an Optimized
     * (sub)class instance. */
    protected Relation optimized(Relation operator) {
        return optimizer.optimized(operator);
    }

    /** Optimize an expression using the Optimizer) */
    protected Relation optimize(Relation expression) {
        return expression.accept(optimizer);
    }

    /** Asserts that `expected` and `optimized` denote the same algebraic
     * expresion. */
    protected void assertSameExpression(Relation expected, Relation optimized) {
        List<Object> refAst = expected.accept(new ToAstVisitor());
        List<Object> optAst = optimized.accept(new ToAstVisitor());
        if (!refAst.equals(optAst)) {
            System.out.println(refAst);
            System.out.println(optAst);
        }
        assertEquals(refAst, optAst);
    }

    /** Asserts that `expression` is properly optimized as `optimized`. This
     * works by asserting that `expected` and `optimized` are same expressions
     * but also by testing that they all denote the same relation value. */
    protected void assertProperlyOptimized(Relation expression,
            Relation expected, Relation optimized) {
        assertSameExpression(expected, optimized);
        assertEquals(expected, expression);
        assertEquals(expression, optimized);
    }

    protected Object toAst(Relation r) {
        return r.accept(new ToAstVisitor());
    }

}
