package jalf;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttrNameTest {

    AttrName RID = AttrName.attr("rid");
    AttrName SID = AttrName.attr("sid");
    AttrName WID = AttrName.attr("wid");

    @Test
    public void testAttrs() {
        AttrName[] expected = new AttrName[]{ SID, RID };
        assertArrayEquals(expected, AttrName.attrs(new String[]{ "sid", "rid" }));
    }

    @Test
    public void testGetName() {
        assertEquals("sid", SID.getName());
    }

    @Test
    public void testItIsComparableByName() {
        assertEquals(0, SID.compareTo(SID));
        assertTrue(SID.compareTo(WID) < 0);
        assertTrue(SID.compareTo(RID) > 0);
    }

    @Test
    public void testEquals(){
        assertEquals(SID, AttrName.attr("sid"));
        assertNotEquals(SID, WID);
    }

    @Test
    public void testHashCode(){
        assertEquals(SID.hashCode(), AttrName.attr("sid").hashCode());
    }

}
