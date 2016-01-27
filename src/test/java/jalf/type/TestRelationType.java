package jalf.type;

import static jalf.DSL.attrs;
import static jalf.DSL.heading;
import static jalf.DSL.relation;
import static jalf.DSL.renaming;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.STATUS;
import static jalf.fixtures.SuppliersAndParts.suppliers;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import jalf.AttrList;
import jalf.AttrName;
import jalf.Relation;
import jalf.Tuple;
import jalf.TypeException;
import jalf.aggregator.Avg;
import jalf.aggregator.Count;
import jalf.aggregator.Max;

public class TestRelationType {

    RelationType type = RelationType.dress(SID, String.class);

    @Test
    public void testInfer() {
        Relation suppliers = suppliers();
        RelationType inferred = RelationType.infer(suppliers.stream());
        assertEquals(suppliers.getType(), inferred);
    }

    @Test
    public void testToTupleType() {
        TupleType expected = TupleType.dress(SID, String.class);
        assertEquals(expected, type.toTupleType());
    }

    @Test
    public void testToAttrList() {
        RelationType type = RelationType.dress(STATUS, Integer.class, SID, String.class);
        AttrList expected = AttrList.attrs(STATUS, SID);
        assertEquals(expected, type.toAttrList());

        // it preserves the insertion order
        List<AttrName> list = Arrays.asList(STATUS, SID);
        assertEquals(list, expected.toList());
    }

    @Test
    public void testEquals() {
        // it is equal to itself
        assertTrue(type.equals(type));
        // it is equal to an equivalent
        assertTrue(type.equals(RelationType.dress(SID, String.class)));
        // it is not equal to another ones
        assertFalse(type.equals(RelationType.dress()));
        assertFalse(type.equals(RelationType.dress(SID, String.class, STATUS, Integer.class)));
        assertFalse(type.equals(RelationType.dress(SID, Integer.class)));
        assertFalse(type.equals(RelationType.dress(NAME, String.class)));
    }

    @Test
    public void testContains() {
        // it contains a proper relation
        Relation r = relation(tuple(SID, "S1"));
        assertTrue(type.contains(r));

        // it does not contain a non relation
        assertFalse(type.contains(null));
        assertFalse(type.contains(12));
        assertFalse(type.contains(Tuple.dress(SID, 12)));

        // it does not contain a relation of another type
        r = relation(heading());
        assertFalse(type.contains(r));
        r = relation(tuple(SID, 12));
        assertFalse(type.contains(r));
        r = relation(tuple(SID, "S1", STATUS, 12));
        assertFalse(type.contains(r));
    }

    @Test
    public void testProject() {
        RelationType from = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType actual = from.project(attrs(SID));
        RelationType expected = type;
        assertEquals(expected, actual);
    }

    @Test
    public void testRename() {
        RelationType from = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType expected = RelationType.dress(NAME, String.class, STATUS, Integer.class);;
        RelationType actual = from.rename(renaming(SID, NAME));
        assertEquals(expected, actual);
    }

    @Test
    public void testJoin() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, CITY, String.class);
        RelationType expected = RelationType.dress(SID, String.class, STATUS, Integer.class, CITY, String.class);
        assertEquals(expected, r1.join(r2));

        // it keeps a natural order
        List<AttrName> list = Arrays.asList(SID, STATUS, CITY);
        assertEquals(list, expected.toAttrList().toList());
    }

    @Test
    public void testUnion() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType expected = RelationType.dress(SID, String.class, STATUS, Integer.class);
        assertEquals(expected, r1.union(r2));
    }

    @Test(expected=TypeException.class)
    public void testUnionDetectsTypeMismatches() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, STATUS, String.class);
        r1.union(r2);
    }

    @Test(expected=TypeException.class)
    public void testUnionDetectsIncompatibleHeadings() {
        RelationType r1 = RelationType.dress(SID, String.class);
        RelationType r2 = RelationType.dress(PID, String.class);
        r1.union(r2);
    }

    @Test
    public void testIntersect() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType expected = RelationType.dress(SID, String.class, STATUS, Integer.class);
        assertEquals(expected, r1.intersect(r2));
    }

    @Test(expected=TypeException.class)
    public void testIntersectDetectsTypeMismatches() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, STATUS, String.class);
        r1.intersect(r2);
    }

    @Test(expected=TypeException.class)
    public void testIntersectDetectsIncompatibleHeadings() {
        RelationType r1 = RelationType.dress(SID, String.class);
        RelationType r2 = RelationType.dress(PID, String.class);
        r1.intersect(r2);
    }

    @Test
    public void testMinus() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType expected = RelationType.dress(SID, String.class, STATUS, Integer.class);
        assertEquals(expected, r1.minus(r2));
    }

    @Test(expected=TypeException.class)
    public void testMinusDetectsTypeMismatches() {
        RelationType r1 = RelationType.dress(SID, String.class, STATUS, Integer.class);
        RelationType r2 = RelationType.dress(SID, String.class, STATUS, String.class);
        r1.minus(r2);
    }

    @Test(expected=TypeException.class)
    public void testMinusDetectsIncompatibleHeadings() {
        RelationType r1 = RelationType.dress(SID, String.class);
        RelationType r2 = RelationType.dress(PID, String.class);
        r1.minus(r2);
    }

    // relationtype tests for summarize

    @Test(expected=TypeException.class)
    public void testSummarizeMismatchesAttrBy() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, STATUS, Integer.class);
        AttrList by = AttrList.attrs(PID);
        AttrName as = AttrName.attr("NEW");
        Count agg = new Count();
        r.summarize(by, agg, as);
    }

    @Test(expected=TypeException.class)
    public void testSummarizeByNotContainAs() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, QTY, Integer.class);
        AttrList by = AttrList.attrs(SID, NAME);
        AttrName as = NAME;
        Avg agg = new Avg(QTY);
        r.summarize(by, agg, as);
    }

    @Test(expected=TypeException.class)
    public void testSummarizeOnIncorrectAggAttrForAvg() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, QTY, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("NEW");
        Avg agg = new Avg(NAME);
        r.summarize(by, agg, as);
    }

    @Test(expected=TypeException.class)
    public void testSummarizeOnIncorrectAggAttrForMax() {
        RelationType r = RelationType.dress(SID, String.class, STATUS, Number.class, QTY, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("NEW");
        Max agg = new Max(STATUS);
        r.summarize(by, agg, as);
    }

    @Test
    public void testSummarizeOnCount() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, STATUS, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("NEW");
        Count agg = new Count();
        RelationType expected = RelationType.dress(SID, String.class, "NEW", Long.class);
        assertEquals(expected, r.summarize(by, agg, as));
    }

    @Test
    public void testSummarizeOnMaxString() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, STATUS, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("NEW");
        Max agg = new Max(NAME);
        RelationType expected = RelationType.dress(SID, String.class, "NEW", String.class);
        assertEquals(expected, r.summarize(by, agg, as));
    }

    @Test
    public void testSummarizeOnMaxInteger() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, STATUS, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("NEW");
        Max agg = new Max(STATUS);
        RelationType expected = RelationType.dress(SID, String.class, "NEW", Integer.class);
        assertEquals(expected, r.summarize(by, agg, as));
    }

    @Test
    public void testSummarizeOnAvg() {
        RelationType r = RelationType.dress(SID, String.class, NAME, String.class, QTY, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("NEW");
        Avg agg = new Avg(QTY);
        RelationType expected = RelationType.dress(SID, String.class, "NEW", Double.class);
        assertEquals(expected, r.summarize(by, agg, as));
    }
}
