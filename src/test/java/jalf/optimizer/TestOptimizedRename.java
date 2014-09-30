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
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrict() {
        Relation suppliers = suppliers();

        // restrict(
        //   rename(suppliers, sid -> supplier_id, city -> lives_in),
        //   lives_in == 'London')
        Relation operator = rename(
                suppliers,
                renaming(SID, SUPPLIER_ID, CITY, LIVES_IN));
        Relation optimized = optimized(operator).
                restrict(Predicate.eq(LIVES_IN, "London"));

        // rename(
        //   restrict(suppliers, city == 'London'),
        //   sid -> supplier_id, city -> lives_in)
        Relation expected = rename(
                restrict(suppliers, Predicate.eq(CITY, "London")),
                renaming(SID, SUPPLIER_ID, CITY, LIVES_IN));
        assertSameExpression(expected, optimized);
    }

    @Test
    public void testRestrictOnJavaPredicate() {
        Relation suppliers = suppliers();
        Predicate opaque = Predicate.java(t -> false);

        // restrict(
        //   rename(suppliers, sid -> supplier_id, city -> lives_in),
        //   [opaque])
        Relation operator = rename(
                suppliers,
                renaming(SID, SUPPLIER_ID, CITY, LIVES_IN));
        Relation optimized = optimized(operator).
                restrict(opaque);

        // restrict(
        //   rename(suppliers, sid -> supplier_id, city -> lives_in),
        //   [opaque])
        Relation expected = restrict(
                rename(suppliers, renaming(SID, SUPPLIER_ID, CITY, LIVES_IN)),
                opaque);
        assertSameExpression(expected, optimized);
    }
}
