package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.relation;
import static jalf.DSL.rename;
import static jalf.DSL.renaming;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.QTY;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.SUPPLIER_ID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.constraint.Keys;

import org.junit.Test;

public class KeyRenameTest {

    public void testRenameTheEntireKey(){
	Relation r = shipments();
	Keys expected = keys(key(attr("RS"), attr("RP")));
	Relation rn = rename(r, renaming(SID, attr("RS"), PID, attr("RP")));
	Keys actual= rn.getKeys();
	assertEquals(expected, actual);

	// test if the header contain the key
	//Heading h = rn.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }


    @Test
    public void testRenameAnAttrOfKey(){
	Relation r = shipments();
	Keys expected = keys(key(SUPPLIER_ID,PID));
	Relation rn = rename(r, renaming(SID, SUPPLIER_ID));
	Keys actual= rn.getKeys();
	assertEquals(expected, actual);

	// test if the header contain the key
	//Heading h = rn.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testRenameNotTheKey(){
	// test renaming if there is no intersection
	// between the key and the renaming attributes
	Relation r = shipments();
	Relation rn = rename(r, renaming(QTY, attr("RQ")));
	Keys actual=  rn.getKeys();
	Keys expected = keys(key(SID,PID));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = rn.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }

    @Test
    public void testRenameWithMultiKey(){
	// test Ps intersect Ks = Ks : Kn = Ks
	Relation r = relation(
		keys(key(attr("A"), attr("C")), key(attr("B")), key(attr("C"),attr("D"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1", attr("D"), "d1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3", attr("D"), "d7"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2", attr("D"), "d5"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1", attr("D"), "d3"),
		tuple(attr("A"), "a3", attr("B"), "b7", attr("C"), "c4", attr("D"), "d4"));

	Relation p = rename(r, renaming(attr("C"), attr("CR")));;
	Keys actual = p.getKeys();
	Keys expected = keys(key(attr("A"), attr("CR")), key(attr("B")), key(attr("CR"),attr("D")));
	assertEquals(expected, actual);
    }

}
