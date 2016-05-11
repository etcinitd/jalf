package jalf.constraint.algebra;

import static jalf.DSL.attr;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.constraint.Keys;

import org.junit.Test;

public class KeyUnionTest {
    @Test
    public void testUnionOperatorWithKey(){
	// union with header compatible
	// k1 union k2
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

	Relation r = r1.union(r2);
	Keys actual = r.getKeys();
	Keys expected = keys(key(attr("A"), attr("B"), attr("C")));
	assertEquals(expected, actual);
    }

    @Test
    public void testUnionOperatorWithMultiKey(){
	// k1 union k2
	Relation r1 = relation(
		keys(key(attr("A"), attr("C")), key(attr("B"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b7", attr("C"), "c4"));

	Relation r2 = relation(
		keys(key(attr("A"), attr("B"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3"),
		tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1"),
		tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2"));

	Relation r = r1.union(r2);
	Keys actual = r.getKeys();
	Keys expected = keys(key(attr("A"), attr("C"), attr("B")));
	assertEquals(expected, actual);
    }

    @Test
    public void testUnionOperatorWithMultiKey2(){
	// k1 union k2
	Relation r1 = relation(
		keys(key(attr("A"), attr("C")), key(attr("B")), key(attr("C"), attr("D"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1", attr("D"), "d1"),
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3", attr("D"), "d3"),
		tuple(attr("A"), "a1", attr("B"), "b3", attr("C"), "c2", attr("D"), "d5"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1", attr("D"), "d0"),
		tuple(attr("A"), "a3", attr("B"), "b7", attr("C"), "c4", attr("D"), "d1"));

	Relation r2 = relation(
		keys(key(attr("A"), attr("B"))),
		tuple(attr("A"), "a1", attr("B"), "b1", attr("C"), "c1",attr("D"), "d1") ,
		tuple(attr("A"), "a1", attr("B"), "b2", attr("C"), "c3", attr("D"), "d2"),
		tuple(attr("A"), "a2", attr("B"), "b4", attr("C"), "c2", attr("D"), "d4"),
		tuple(attr("A"), "a2", attr("B"), "b5", attr("C"), "c1", attr("D"), "d0"),
		tuple(attr("A"), "a3", attr("B"), "b1", attr("C"), "c2", attr("D"), "d1"));

	Relation r = r1.union(r2);
	Keys actual = r.getKeys();
	Keys expected = keys(key(attr("A"), attr("B"), attr("C"), attr("D")));
	assertEquals(expected, actual);
    }

}
