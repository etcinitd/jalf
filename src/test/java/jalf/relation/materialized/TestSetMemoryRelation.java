package jalf.relation.materialized;

import jalf.Relation;
import jalf.relation.algebra.Identity;
import org.junit.Test;

import static jalf.DSL.tuple;
import static jalf.test.fixtures.SuppliersAndParts.SID;
import static jalf.util.CollectionUtils.setOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSetMemoryRelation {

    Relation empty = new SetMemoryRelation(setOf());

    Relation s1 = new SetMemoryRelation(setOf(
            tuple(SID, "S1")
    ));

    Relation s1bis = new SetMemoryRelation(setOf(
            tuple(SID, "S1")
    ));
    
    Relation s2 = new SetMemoryRelation(setOf(
            tuple(SID, "S2")
    ));

    @Test
    public void testEqualsAgainstAnotherOne() {
        assertTrue(s1.equals(s1));
        assertTrue(s1.equals(s1bis));
        assertFalse(s1.equals(s2));
        assertFalse(s1.equals(empty));
        assertFalse(empty.equals(s1));
    }

    @Test
    public void testEqualsAgainstAStream() {
        assertTrue(s1.equals(new Identity(s1)));
        assertTrue(s1.equals(new Identity(s1bis)));
        assertFalse(s1.equals(new Identity(s2)));
        assertFalse(s1.equals(new Identity(empty)));
        assertFalse(empty.equals(new Identity(s1)));
    }

}
