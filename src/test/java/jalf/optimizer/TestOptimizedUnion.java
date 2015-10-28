package jalf.optimizer;
import static jalf.DSL.attrs;
import static jalf.DSL.eq;
import static jalf.DSL.project;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.DSL.union;
import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.SID;

import org.junit.Test;

import jalf.Relation;
public class TestOptimizedUnion extends OptimizerTest {

    Relation left = relation(
            tuple(SID, "S1", NAME, "N1", CITY, "C1"),
            tuple(SID, "S2", NAME, "N2", CITY, "C2"),
            tuple(SID, "S3", NAME, "N3", CITY, "C3"),
            tuple(SID, "S4", NAME, "N4", CITY, "C4")
            );

    Relation right = relation(
            tuple(SID, "S2", NAME, "N2", CITY, "C2"),
            tuple(SID, "S7", NAME, "N7", CITY, "C7"),
            tuple(SID, "S5", NAME, "N5", CITY, "C5")
            );

    @Test
    public void testProject(){
        Relation operator = union(left, right);
        Relation optimized = optimized(operator).project(attrs(NAME, CITY));

        Relation expected = union(
                project(left, attrs(NAME, CITY)),
                project(right, attrs(NAME, CITY))
                );
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrict(){
        Relation operator =  union(left, right);
        Relation optimized = optimized(operator).restrict(eq(CITY, "C7"));

        Relation expected = left
                .restrict(eq(CITY, "C7"))
                .union(right.restrict(eq(CITY, "C7")));
        assertSameExpression(expected, optimized);
    }

}
