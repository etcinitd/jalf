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

    protected Relation optimized(Relation operator) {
        return optimizer.optimized(operator);
    }

    protected void assertSameExpression(Relation expected, Relation optimized) {
        List<Object> refAst = expected.accept(new ToAstVisitor());
        List<Object> optAst = optimized.accept(new ToAstVisitor());
        assertEquals(refAst, optAst);
    }

    protected Object toAst(Relation r) {
        return r.accept(new ToAstVisitor());
    }

}
