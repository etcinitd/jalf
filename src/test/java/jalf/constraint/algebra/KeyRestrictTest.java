package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.eq;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.PID;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.shipments;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.constraint.Keys;

import org.junit.Test;

public class KeyRestrictTest {
    @Test
    public void testRestrictOperatorWithKey(){
	// restrict does not have impact on the key
	Relation r = shipments();

	Relation rs = r.restrict(eq(SID, "S1"));
	Keys actual =rs.getKeys();
	Keys expected = keys(key(SID, PID));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = rs.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());

    }

    @Test
    public void testRestrictWithMultiKey(){
	// test Ps intersect Ks = Ks : Kn = Ks
	Relation r = relation(
		keys(key(attr("A"), attr("C")), key(attr("B")), key(attr("C"),attr("D"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1", attr("D"), "d1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3", attr("D"), "d7"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2", attr("D"), "d5"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1", attr("D"), "d3"),
		tuple(attr("A"), "a3", attr("B"), "b7", attr("C"), "c4", attr("D"), "d4"));

	Relation rs = r.restrict(eq(attr("A"), "a1"));
	Keys actual =rs.getKeys();
	Keys expected = keys(key(attr("A"), attr("C")), key(attr("B")), key(attr("C"),attr("D")));
	assertEquals(expected, actual);
    }

}
