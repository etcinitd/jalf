package jalf.optimizer;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import jalf.*;

import org.junit.Test;

public class TestOptimizedRename extends OptimizerTest {

    @Test
    public void testProject() {
        Relation suppliers = suppliers();

        // project(
        //   rename(suppliers, sid -> supplier_id, city -> lives_in),
        //   [supplier_id, name])
        Relation operator = rename(
                suppliers,
                renaming(SID, SUPPLIER_ID, CITY, LIVES_IN));
        Relation optimized = optimized(operator).
                project(attrs(SUPPLIER_ID, NAME));

        // rename(
        //   project(suppliers, [sid, name]),
        //   sid -> supplier_id)
        Relation expected = rename(
                project(suppliers, attrs(SID, NAME)),
                renaming(SID, SUPPLIER_ID));
        System.out.println(toAst(expected));
        System.out.println(toAst(optimized));
        assertSameExpression(expected, optimized);
    }

}
