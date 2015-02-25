package jalf.relation.csv;

import static jalf.DSL.heading;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import static jalf.fixtures.SuppliersAndParts.CITY;
import static jalf.fixtures.SuppliersAndParts.NAME;
import static jalf.fixtures.SuppliersAndParts.SID;
import static jalf.fixtures.SuppliersAndParts.STATUS;
import static org.junit.Assert.assertEquals;
import jalf.Relation;
import jalf.type.RelationType;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Supplier;

import org.junit.Test;

public class CsvRelationTest {

    RelationType suppliersType = RelationType.dress(
        heading(SID, String.class, NAME, String.class, STATUS, String.class, CITY, String.class)
    );

    @Test
    public void testGetType() throws IOException {
        CsvRelation r = new CsvRelation(suppliersStreamSupplier());
        assertEquals(suppliersType, r.getType());
    }

    @Test
    public void testEquals() throws IOException {
        Relation expected = relation(
            suppliersType,
            tuple(SID, "S1", NAME, "Smith", STATUS, "20", CITY, "London"),
            tuple(SID, "S2", NAME, "Jones", STATUS, "10", CITY, "Paris"),
            tuple(SID, "S3", NAME, "Blake", STATUS, "30", CITY, "Paris"),
            tuple(SID, "S4", NAME, "Clark", STATUS, "20", CITY, "London"),
            tuple(SID, "S5", NAME, "Adams", STATUS, "30", CITY, "Athens")
        );
        CsvRelation r = new CsvRelation(suppliersStreamSupplier());
        assertEquals(expected, r);
    }

    ///

    private Supplier<InputStream> suppliersStreamSupplier() {
        return (() -> {
            return CsvRelationTest.class.getResourceAsStream("suppliers.csv");
        });
    }

}
