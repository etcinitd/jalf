package jalf;

import static org.junit.Assert.assertEquals;
import static jalf.DSL.*;

import org.junit.Test;

public class RenamingTest {

    AttrName<String> sid = attr("sid", String.class);
    AttrName<String> ssid = attr("ssid", String.class);
    AttrName<String> name = attr("name", String.class);
    AttrName<String> sname = attr("sname", String.class);
    AttrName<String> city = attr("city", String.class);

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
    public void testItSupportsIntensionRenaming() {
        AttrName<?> SID = attr("SID");
        Renaming r = Renaming.intension(a -> attr(a.toString().toUpperCase()));
        assertEquals(r.apply(sid), SID);
    }

    @Test
    public void testItSupportsPrefixRenaming() {
        Renaming r = Renaming.prefix("foo");
        assertEquals(r.apply(sid), attr("foosid"));
    }

    @Test
    public void testItSupportsSuffixRenaming() {
        Renaming r = Renaming.suffix("foo");
        assertEquals(r.apply(sid), attr("sidfoo"));
    }

}
