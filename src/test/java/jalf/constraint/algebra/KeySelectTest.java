package jalf.constraint.algebra;

import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.select;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.suppliers;
import static org.junit.Assert.assertEquals;
import jalf.AttrName;
import jalf.Relation;
import jalf.Selection;
import jalf.SelectionMember;
import jalf.Type;
import jalf.constraint.Keys;

import org.junit.Test;

public class KeySelectTest {

    @Test
    public void testSelectOperatorWithKey(){
	AttrName LETTER = AttrName.attr("letter");

	SelectionMember letterMember = SelectionMember.fn(
		Type.scalarType(String.class),
		t -> {
		    String name = (String) t.get(NAME);
		    return name.substring(name.length() - 1, name.length());
		});
	Selection sel = Selection.varargs(LETTER, letterMember);
	Relation rs = select(suppliers(), sel);
	Keys actual= rs.getKeys();
	Keys expected = keys(key(LETTER));
	assertEquals(expected, actual);
    }

}
