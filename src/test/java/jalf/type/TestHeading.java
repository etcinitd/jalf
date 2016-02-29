package jalf.type;

import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.STATUS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import jalf.AttrList;
import jalf.AttrName;
import jalf.Renaming;
import jalf.Type;
import jalf.TypeException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestHeading {

    Heading h = Heading.dress(SID, String.class);

    @Test
    public void testInfer() {
        Map<AttrName, Object> t = new HashMap<>();
        t.put(SID,  "S1");
        t.put(STATUS, 20);
        Heading h = Heading.infer(t);
        assertEquals(h, Heading.dress(SID, String.class, STATUS, Integer.class));
    }

    @Test
    public void testDress() {
        AttrName[] names = new AttrName[]{ SID, STATUS };
        Heading got = Heading.dress(names, (name)-> {
            return name.equals(SID) ?
                    Type.scalarType(String.class) :
                        Type.scalarType(Integer.class);
        });
        Heading expected = Heading.dress(SID, String.class, STATUS, Integer.class);
        assertEquals(expected, got);
    }

    @Test
    public void testLeastCommonSuperHeading() {
        // it simply returns an equal heading on identical
        assertEquals(h, Heading.leastCommonSuperHeading(h,  h));

        // it throws when not having the same attributes
        try {
            Heading h1 = Heading.dress(SID, String.class);
            Heading h2 = Heading.dress(PID, String.class);
            Heading.leastCommonSuperHeading(h1, h2);
            assertFalse(true);
        } catch (TypeException ex) {
            assertEquals("Headings must have same attributes", ex.getMessage());
        }
    }

    @Test
    public void testGetTypeOf() {
        Type<?> expected = Type.scalarType(String.class);
        assertEquals(expected, h.getTypeOf(SID));
    }

    @Test
    public void testToAttrList() {
        // it is as expected
        Heading h = Heading.dress(SID, String.class, STATUS, Integer.class);
        AttrList expected = AttrList.attrs(SID, STATUS);
        assertEquals(expected, h.toAttrList());

        // it keeps the original 'insertion' order
        h = Heading.dress(STATUS, Integer.class, SID, String.class);
        List<AttrName> list = AttrList.attrs(STATUS, SID).toList();
        assertEquals(list, h.toAttrList().toList());
    }

    @Test
    public void testProject() {
        Heading h = Heading.dress(SID, String.class, STATUS, Integer.class);
        Heading expected = Heading.dress(STATUS, Integer.class);
        assertEquals(expected, h.project(AttrList.attrs(STATUS)));
    }
    @Test
    public void testSummarize() {
        Heading h = Heading.dress(SID, String.class, NAME, String.class, STATUS, Integer.class);
        AttrList by = AttrList.attrs(SID);
        AttrName as = AttrName.attr("new");
        Heading expected = Heading.dress(SID, String.class, "new", Integer.class);
        Heading actual = h.summarize(by, as, Type.scalarType(Integer.class));
        assertEquals(expected, actual);
    }

    @Test
    public void testJoin() {
        Heading h1 = Heading.dress(SID, String.class, STATUS, Integer.class);
        Heading h2 = Heading.dress(SID, String.class, CITY, String.class);
        Heading expected = Heading.dress(SID, String.class, STATUS, Integer.class, CITY, String.class);
        assertEquals(expected, h1.join(h2));

        // it keeps a natural attribute names order
        List<AttrName> list = Arrays.asList(SID, STATUS, CITY);
        assertEquals(list, expected.toAttrList().toList());
    }

    @Test
    public void testRename() {
        Heading h = Heading.dress(SID, String.class, STATUS, Integer.class);
        Heading expected = Heading.dress(PID, String.class, STATUS, Integer.class);
        assertEquals(expected, h.rename(Renaming.extension(SID, PID)));
    }

    @Test
    public void testHashCode() {
        // it does not depend on the ordering
        Heading h1 = Heading.dress(SID, String.class, STATUS, Integer.class);
        Heading h2 = Heading.dress(STATUS, Integer.class, SID, String.class);
        assertEquals(h1.hashCode(), h2.hashCode());
    }

    @Test
    public void testEquals() {
        // it is equal to itself
        assertTrue(h.equals(h));
        // it is equal to an equivalent
        assertTrue(h.equals(Heading.dress(SID, String.class)));
        // it is not equal to another ones
        assertFalse(h.equals(Heading.dress()));
        assertFalse(h.equals(Heading.dress(SID, String.class, STATUS, Integer.class)));
        assertFalse(h.equals(Heading.dress(SID, Integer.class)));
        assertFalse(h.equals(Heading.dress(NAME, String.class)));
    }

}
