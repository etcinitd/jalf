package jalf.dsl;

import jalf.*;
import jalf.type.TupleType;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class TupleTest {

    @Test
    public void testItInfersTheTypeIfNotProvided() {
        Tuple t = tuple(SID, "S1", STATUS, 20);
        TupleType expected = TupleType.dress(SID, String.class, STATUS, Integer.class);
        assertEquals(expected, t.getType());
    }

}
