package jalf.relation.materialized;

import java.util.stream.Stream;

import jalf.*;
import jalf.type.RelationType;

import org.junit.*;

import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class TestEmptyRelation {

    @Test
    public void testStream() {
        RelationType type = RelationType.dress(SID, String.class);
        Relation r = EmptyRelation.factor(type);
        Stream<Tuple> stream = r.stream();
        assertFalse(stream.findAny().isPresent());
    }

    @Test
    public void testEquals() {
        RelationType type = RelationType.dress(SID, String.class);
        Relation empty = EmptyRelation.factor(type);

        Relation other = SetMemoryRelation.tuples(type);
        assertEquals(empty, other);

        other = SetMemoryRelation.tuples(type, Tuple.varargs(SID, "S1"));
        assertNotEquals(empty, other);
    }

}
