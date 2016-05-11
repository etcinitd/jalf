package jalf.constraint;

import static jalf.DSL.attr;
import static jalf.DSL.heading;
import static jalf.DSL.key;
import static jalf.DSL.relation;
import static jalf.DSL.renaming;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import jalf.AttrName;
import jalf.Relation;
import jalf.Renaming;

public class KeyTest {

    @Test
    public void testHashCode() {
        Key k1 = key(SID, PID);
        Key k2 = key(SID, PID);
        Key k3 = key(PID, SID);
        Key k4 = key(PID, QTY);
        assertEquals(k1.hashCode(), k2.hashCode());
        assertEquals(k1.hashCode(), k3.hashCode());
        assertNotEquals(k1.hashCode(), k4.hashCode());
    }

    @Test
    public void testEquals() {
        Key k1 = key(SID, PID);
        Key k2 = key(PID, SID);
        Key k3 = key(PID, QTY);
        assertEquals(k1, k1);
        assertEquals(k1, k2);
        assertNotEquals(k1, k3);
    }

    @Test
    public void testCheckIfItisCandidateKey(){
        // test check if the key can be candidate
        Relation r = shipments();
        Key k1 = key(SID, PID);
        assertEquals(k1,r.getKeys().toList().get(0));
    }

    @Test
    public void testHeaderIsKeyIfKeyNotSpecified(){
        // the key is the header if the key entered don't support the uniqueness constraint
        Relation r = relation(
                heading(SID, String.class, PID, String.class, QTY, Integer.class),
                tuple(SID, "S1", PID, "P1", QTY, 300));
        Key k1 = key(SID,PID,QTY);
        assertEquals(r.getKeys().toList().get(0),k1);
    }

    @Test
    public void testCheckKeyUniquenessTrue(){
        Relation r = shipments();
        Key k = key(SID, PID);
        assertTrue(k.check(r));
    }

    @Test
    public void testCheckKeyUniquenessFalse(){
        Relation r = shipments();
        Key k = key(SID);
        assertFalse(k.check(r));
    }

    @Test
    public void testRenameTheKey(){
        Key k1 = key(SID,PID);
        Renaming rn = renaming(SID, attr("RS"), PID, attr("RP"));
        Key actual = k1.rename(rn);
        Key expected = key(attr("RS"), attr("RP"));
        assertEquals(expected, actual);
    }

    @Test
    public void testKeyRelationEquality(){
        Relation r1 = shipments();
        Relation r2 = shipments();
        assertEquals(r1.getKeys(), r2.getKeys());
    }

    @Test
    public void testKeyRelationEquality1(){
        AttrName LETTER = AttrName.attr("letter");
        Relation r1 = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
                );
        Relation r2 = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
                );
        assertEquals(r1.getKeys(), r2.getKeys());
    }

}
