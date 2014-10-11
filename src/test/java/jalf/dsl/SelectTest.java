package jalf.dsl;

import jalf.*;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.*;

public class SelectTest {

    @Test
    public void testItWorksAsExpected() {
        AttrName LETTER = AttrName.attr("letter");
        Relation expected = relation(
                tuple(LETTER, "h"),
                tuple(LETTER, "s"),
                tuple(LETTER, "e"),
                tuple(LETTER, "k")
        );
        SelectionMember letterMember = SelectionMember.fn(
                Type.scalarType(String.class),
                t -> {
                    String name = (String) t.get(NAME);
                    return name.substring(name.length() - 1, name.length());
                });
        Selection sel = Selection.varargs(LETTER, letterMember);
        Relation actual = select(suppliers(), sel);
        assertEquals(expected, actual);
    }

}
