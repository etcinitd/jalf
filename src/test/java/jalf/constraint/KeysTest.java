package jalf.constraint;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.renaming;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jalf.Renaming;

public class KeysTest {


    @Test
    public void testHashCode() {
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys k2 =keys(key(SID, PID), key(SID,PID, QTY));
        Keys k3 = keys(key(PID, SID), key(SID,PID, QTY));
        Keys k4 =keys(key(PID, QTY));
        assertEquals(k1.hashCode(), k2.hashCode());
        assertEquals(k1.hashCode(), k3.hashCode());
        assertNotEquals(k1.hashCode(), k4.hashCode());
    }


    @Test
    public void testEquals() {
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys k2 = keys(key(PID, SID), key(SID,PID, QTY));
        Keys k3 =keys(key(PID, QTY));
        assertEquals(k1, k1);
        assertEquals(k1, k2);
        assertNotEquals(k1, k3);
    }

    @Test
    public void testToString(){
        Keys k = keys(key(SID, PID), key(SID,PID, QTY));
        String actual = k.toString();
        String expected = "keys(key( sid, pid), key( sid, pid, qty))\n";
        assertEquals(expected, actual);
    }

    /*
     * test asserttrue if there are no super key of a key
     */
    @Test
    public void testareKeysValidTrue(){
        Keys k = keys(key(attr("C"), PID), key(SID,PID, QTY));
        assertTrue(k.areKeysValid());
    }

    /*
     * test assertfalse if there are super key of a key
     */
    @Test
    public void testareKeysValidFalse(){
        Keys k = keys(key(SID, PID), key(SID, PID, QTY));
        assertFalse(k.areKeysValid());
    }

    @Test
    public void testareKeysValidFalse2(){
        Keys k = keys(key(SID, PID, QTY), key(SID));
        assertFalse(k.areKeysValid());
    }

    @Test
    public void testComplexUnion() {
        Keys k1 = keys(key(SID, PID), key(SID, PID, QTY));
        Keys k2 = keys(key(PID, QTY));
        Keys actual = k1.complexUnion(k2);
        Keys expected = keys(key(SID, PID, QTY));
        assertEquals(expected, actual);
    }

    @Test
    public void testComplexUnionMultiKeys() {
        Keys k1 = keys(key(SID, PID), key(SID, NAME, QTY));
        Keys k2 = keys(key(PID, QTY));
        Keys actual = k1.complexUnion(k2);
        Keys expected = keys(key(SID, PID, QTY), key(SID, NAME, QTY, PID));
        assertEquals(expected, actual);
    }

    @Test
    public void testSimpleUnion() {
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys k2 =keys(key(PID, QTY));
        Keys actual = k1.simpleUnion(k2);
        Keys expected=keys(key(SID, PID), key(SID, PID, QTY), key(PID, QTY));
        assertEquals(expected, actual);
    }

    @Test
    public void testRename(){
        Renaming rn = renaming(QTY, attr("QTY_Max"));
        Keys k1 = keys(key(SID, PID), key(SID,PID, QTY));
        Keys actual=k1.rename(rn);
        Keys expected=keys(key(SID, PID), key(SID,PID, attr("QTY_Max")));
        assertEquals(expected, actual);
    }

}
