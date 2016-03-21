package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.minus;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.SID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import jalf.Relation;
import jalf.constraint.Keys;

import org.junit.Test;

public class KeyMinusTest {
    @Test
    public void testMinusOperatorWithKey(){
	// minus with header compatible
	// k1
	Relation r1 = relation(
		keys(key(attr("A"), attr("C"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b3", attr("C"), "c4"));

	Relation r2 = relation(
		keys(key(attr("A"), attr("B"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2"));

	Relation r = r1.minus(r2);
	Keys actual = r.getKeys();
	Keys expected = keys(key(attr("A"), attr("C")));
	assertEquals(expected, actual);
	// test if the header contain the key
	//Heading h = r.getType().getHeading();
	//AttrList l =  h.toAttrList().intersect(actual.toAttrList());
	//assertEquals(l,actual.toAttrList());
    }


    @Test
    public void testItWorksAsExpectedSameKey(){
	Relation left = relation(
		keys(key(SID)),
		tuple(SID, "S1",NAME ,"Berth"),
		tuple(SID, "S2",NAME ,"Max"),
		tuple(SID, "S3",NAME ,"Tom"),
		tuple(SID, "S4",NAME ,"Jojo")
		);
	Relation right = relation(
		keys(key(SID)),
		tuple(SID, "S2",NAME ,"Max"),
		tuple(SID, "S3",NAME ,"Tom"),
		tuple(SID, "S5",NAME ,"Leny")
		);
	Relation expected = relation(
		keys(key(SID)),
		tuple(SID, "S1",NAME ,"Berth"),
		tuple(SID, "S4",NAME ,"Jojo")
		);
	Relation actual = minus(left, right);
	//verif key
	assertEquals(right.getKeys(), left.getKeys());
	assertEquals(left.getKeys(), actual.getKeys());
	// real test
	assertEquals(expected, actual);

    }

    @Test
    public void testItWorksAsExpectedNotSameKey(){
	Relation left = relation(
		keys(key(SID)),
		tuple(SID, "S1",NAME ,"Berth"),
		tuple(SID, "S2",NAME ,"Max"),
		tuple(SID, "S3",NAME ,"Tom"),
		tuple(SID, "S4",NAME ,"Jojo")
		);
	Relation right = relation(
		tuple(SID, "S2",NAME ,"Max"),
		tuple(SID, "S3",NAME ,"Tom"),
		tuple(SID, "S5",NAME ,"Leny")
		);
	Relation expected = relation(
		keys(key(SID)),
		tuple(SID, "S1",NAME ,"Berth"),
		tuple(SID, "S4",NAME ,"Jojo")
		);
	Relation actual = minus(left, right);
	//verif key
	assertNotEquals(right.getKeys(), left.getKeys());
	assertEquals(left.getKeys(), actual.getKeys());

	// real test
	assertEquals(expected, actual);


    }

    @Test
    public void testItWorksAsExpectedMultiKeys(){
	Relation left = relation(
		keys(key(SID), key(NAME)),
		tuple(SID, "S1",NAME ,"Berth"),
		tuple(SID, "S2",NAME ,"Max"),
		tuple(SID, "S3",NAME ,"Tom"),
		tuple(SID, "S4",NAME ,"Jojo")
		);
	Relation right = relation(
		tuple(SID, "S2",NAME ,"Max"),
		tuple(SID, "S3",NAME ,"Tom"),
		tuple(SID, "S5",NAME ,"Leny")
		);
	Relation expected = relation(
		keys(key(SID), key(NAME)),
		tuple(SID, "S1",NAME ,"Berth"),
		tuple(SID, "S4",NAME ,"Jojo")
		);
	Relation actual = minus(left, right);
	//verif key
	assertNotEquals(right.getKeys(), left.getKeys());
	assertEquals(left.getKeys(), actual.getKeys());

	// real test
	assertEquals(expected, actual);

    }

}
