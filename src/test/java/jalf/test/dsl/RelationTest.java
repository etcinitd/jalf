package jalf.test.dsl;

import jalf.*;
import jalf.type.RelationType;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.test.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class RelationTest {

    @Test
    public void testItAllowsSpecifyingTheType() {
        Relation r = relation(
            RelationType.varargs(SID, String.class),
            tuple(SID, "S1"),
            tuple(SID, "S2")
        );
        assertEquals(2, r.count());
    }

    @Test
    public void testItAllowsNoTupleProvidedTheTypeIsSpecified() {
        Relation r = relation(
                RelationType.varargs(SID, String.class)
        );
        assertEquals(0, r.count());
    }

    @Test(expected = TypeException.class)
    public void testItRaisesWhenNoTupleAndNoType() {
        relation();
    }

    @Test(expected = TypeException.class)
    public void testItRaisesWhenTupleTypeMismatch() {
        relation(
                RelationType.varargs(SID, String.class),
                tuple(SID, "S1"),
                tuple(SID, 10)
        );
    }

    @Test(expected = TypeException.class)
    public void testItRaisesWhenTypeInferenceFails() {
        relation(
                tuple(SID, "S1"),
                tuple(NAME, "S2")
        );
    }

}
