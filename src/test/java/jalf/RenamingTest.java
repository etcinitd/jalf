package jalf;

import org.junit.Test;

import static jalf.DSL.attr;
import static org.junit.Assert.*;

public class RenamingTest {

    AttrName sid = attr("sid");
    AttrName ssid = attr("ssid");
    AttrName name = attr("name");
    AttrName sname = attr("sname");
    AttrName city = attr("city");

    @Test
    public void testItSupportsExtensionRenaming() {
        Renaming r = Renaming.extension(sid, ssid, name, sname);
        assertEquals(r.apply(sid), ssid);
        assertEquals(r.apply(name), sname);
    }

    @Test
    public void testExtensionRenamingIsTotal() {
        Renaming r = Renaming.extension(sid, ssid, name, sname);
        assertEquals(r.apply(city), city);
    }

    @Test
    public void testExtensionRenamingIsReversible() {
        Renaming r = Renaming.extension(sid, ssid, name, sname);
        assertTrue(r.isReversible());
        Renaming reverse = Renaming.extension(ssid, sid, sname, name);
        assertEquals(reverse, r.reverse());
    }

    @Test
    public void testItSupportsIntensionRenaming() {
        AttrName SID = attr("SID");
        Renaming r = Renaming.intension(a -> attr(a.getName().toUpperCase()));
        assertEquals(r.apply(sid), SID);
    }

    @Test
    public void testItSupportsPrefixRenaming() {
        Renaming r = Renaming.prefix("foo");
        assertEquals(r.apply(sid), attr("foosid"));
    }

    @Test
    public void testPrefixRenamingIsReversible() {
        Renaming r = Renaming.prefix("foo");
        assertTrue(r.isReversible());
        assertEquals(attr("sid"), r.reverse().apply(attr("foosid")));
    }

    @Test
    public void testItSupportsSuffixRenaming() {
        Renaming r = Renaming.suffix("foo");
        assertEquals(r.apply(sid), attr("sidfoo"));
    }

    @Test
    public void testSuffixRenamingIsReversible() {
        Renaming r = Renaming.suffix("foo");
        assertTrue(r.isReversible());
        assertEquals(attr("sid"), r.reverse().apply(attr("sidfoo")));
    }

}
