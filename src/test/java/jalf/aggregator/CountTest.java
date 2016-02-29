package jalf.aggregator;

import static jalf.DSL.count;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class CountTest {

    @Test
    public void testItWorksAsExpectedbyCount() {
        Count c = count();
        shipments().stream().forEach(t -> c.accumulate(t));
        long expected = shipments().cardinality();
        assertEquals(expected, c.finish().longValue());
    }

    @Test
    public void testItWrongWorksbyCount() {
        Count c = count();
        shipments().stream().forEach(t -> c.accumulate(t));
        long expected = shipments().cardinality() + 1;
        assertNotEquals(expected, c.finish().longValue());
    }
}
