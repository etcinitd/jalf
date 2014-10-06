package jalf;

import static org.junit.Assert.assertTrue;
import jalf.predicate.False;
import jalf.predicate.True;
import jalf.relation.algebra.Dee;
import jalf.relation.algebra.Dum;

import org.junit.Test;

public class TestConstants {

    @Test
    public void testTrue() {
        assertTrue(True.instance() == DSL.TRUE);
        assertTrue(True.instance() == Predicate.TRUE);
        assertTrue(DSL.TRUE == Predicate.TRUE);
    }

    @Test
    public void testFalse() {
        assertTrue(False.instance() == DSL.FALSE);
        assertTrue(False.instance() == Predicate.FALSE);
        assertTrue(DSL.FALSE == Predicate.FALSE);
    }

    @Test
    public void testDum() {
        assertTrue(Dum.instance() == DSL.DUM);
        assertTrue(Dum.instance() == Relation.DUM);
        assertTrue(DSL.DUM == Relation.DUM);
    }

    @Test
    public void testDee() {
        assertTrue(Dee.instance() == DSL.DEE);
        assertTrue(Dee.instance() == Relation.DEE);
        assertTrue(DSL.DEE == Relation.DEE);
    }

}
