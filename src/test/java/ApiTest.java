import jalf.Relation;
import org.junit.Test;

import static jalf.util.Utils.*;
import static jalf.JAlf.project;
import static org.junit.Assert.assertEquals;

/**
 * @author amirm
 */
public class ApiTest {
    // TODO rename, restrict, join

    @Test
    public void testProject() {
        Relation suppliers = relation(
                tuple("name", "S1", "city", "London"),
                tuple("name", "S2", "city", "Paris")
        );

        suppliers = project(suppliers, attrs("city"));

        assertEquals(2, suppliers.count());
    }

}
