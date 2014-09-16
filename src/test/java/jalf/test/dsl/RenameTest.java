package jalf.test.dsl;

import static jalf.DSL.relation;
import static jalf.DSL.rename;
import static jalf.DSL.renaming;
import static jalf.DSL.tuple;
import static jalf.test.fixtures.SuppliersAndParts.CITY;
import static jalf.test.fixtures.SuppliersAndParts.NAME;
import static jalf.test.fixtures.SuppliersAndParts.SID;
import static jalf.test.fixtures.SuppliersAndParts.STATUS;
import static jalf.test.fixtures.SuppliersAndParts.SUPPLIER_ID;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.Renaming;

import org.junit.Test;

public class RenameTest {

    Relation SOURCE = relation(
        tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London")
    );

    @Test
    public void testItSupportsAnExplicitRenaming() {
        Relation expected = relation(
            tuple(SUPPLIER_ID, "S1", NAME, "Smith", STATUS, 20, CITY, "London")
        );
        Relation actual = rename(SOURCE, renaming(SID, SUPPLIER_ID));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsPrefixRenaming() {
        Relation expected = relation(
            tuple("s_sid", "S1", "s_name", "Smith", "s_status", 20, "s_city", "London")
        );
        Relation actual = rename(SOURCE, Renaming.prefix("s_"));
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsSuffixRenaming() {
        Relation expected = relation(
            tuple("sid_s", "S1", "name_s", "Smith", "status_s", 20, "city_s", "London")
        );
        Relation actual = rename(SOURCE, Renaming.suffix("_s"));
        assertEquals(expected, actual);
    }

}
