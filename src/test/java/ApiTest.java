import static jalf.JAlf.project;
import static jalf.test.fixtures.SuppliersAndParts.CITY;
import static jalf.test.fixtures.SuppliersAndParts.NAME;
import static jalf.test.fixtures.SuppliersAndParts.SID;
import static jalf.test.fixtures.SuppliersAndParts.suppliers;
import static jalf.util.Utils.attrs;
import static jalf.util.Utils.relation;
import static jalf.util.Utils.tuple;
import static org.junit.Assert.assertEquals;
import jalf.Relation;

import org.junit.Test;

/**
 * @author amirm
 */
public class ApiTest {
    // TODO rename, restrict, join

    @Test
    public void testProject() {
		Relation expected = relation(
			tuple(SID, "S1", NAME, "Smith"),
			tuple(SID, "S2", NAME, "Jones"),
			tuple(SID, "S3", NAME, "Blake"),
			tuple(SID, "S4", NAME, "Clark"),
			tuple(SID, "S5", NAME, "Adams")
		);

        Relation actual = project(suppliers(), attrs(CITY));

        assertEquals(expected, actual);
        assertEquals(2, actual.count());
    }

}
