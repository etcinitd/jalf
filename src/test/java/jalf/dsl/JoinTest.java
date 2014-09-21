package jalf.dsl;

import jalf.Relation;

import org.junit.Test;

import static jalf.DSL.*;
import static jalf.fixtures.SuppliersAndParts.*;
import static org.junit.Assert.assertEquals;

public class JoinTest {

    @Test
    public void testItSupportsJoinOnOneAttribute() {
        Relation expected = relation(
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P1", QTY, 300),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P2", QTY, 200),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P3", QTY, 400),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P4", QTY, 200),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P5", QTY, 100),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P6", QTY, 100),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris",  PID, "P1", QTY, 300),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris",  PID, "P2", QTY, 400),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris",  PID, "P2", QTY, 200),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London", PID, "P2", QTY, 200),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London", PID, "P4", QTY, 300),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London", PID, "P5", QTY, 400)
        );
        Relation actual = join(suppliers(), shipments());
        assertEquals(expected, actual);
    }

    @Test
    public void testItSupportsJoinNoCommonAttribute() {
        Relation left = relation(
                tuple(SID, "S1"),
                tuple(SID, "S2")
        );
        Relation right = relation(
                tuple(PID, "P1"),
                tuple(PID, "P2"),
                tuple(PID, "P3")
        );
        Relation expected = relation(
                tuple(SID, "S1", PID, "P1"),
                tuple(SID, "S1", PID, "P2"),
                tuple(SID, "S1", PID, "P3"),
                tuple(SID, "S2", PID, "P1"),
                tuple(SID, "S2", PID, "P2"),
                tuple(SID, "S2", PID, "P3")
        );
        assertEquals(expected, join(left, right));
    }

    @Test
    public void testItSupportsJoinOnJoin() {
        Relation expected = relation(
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P1", QTY, 300, P_NAME, "Nut",   COLOR, "Red",   WEIGHT, 12.0, P_CITY, "London"),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P2", QTY, 200, P_NAME, "Bolt",  COLOR, "Green", WEIGHT, 17.0, P_CITY, "Paris"),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P3", QTY, 400, P_NAME, "Screw", COLOR, "Blue",  WEIGHT, 17.0, P_CITY, "Oslo"),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P4", QTY, 200, P_NAME, "Screw", COLOR, "Red",   WEIGHT, 14.0, P_CITY, "London"),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P5", QTY, 100, P_NAME, "Cam",   COLOR, "Blue",  WEIGHT, 12.0, P_CITY, "Paris"),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London", PID, "P6", QTY, 100, P_NAME, "Cog",   COLOR, "Red",   WEIGHT, 19.0, P_CITY, "London"),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris",  PID, "P1", QTY, 300, P_NAME, "Nut",   COLOR, "Red",   WEIGHT, 12.0, P_CITY, "London"),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris",  PID, "P2", QTY, 400, P_NAME, "Bolt",  COLOR, "Green", WEIGHT, 17.0, P_CITY, "Paris"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris",  PID, "P2", QTY, 200, P_NAME, "Bolt",  COLOR, "Green", WEIGHT, 17.0, P_CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London", PID, "P2", QTY, 200, P_NAME, "Bolt",  COLOR, "Green", WEIGHT, 17.0, P_CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London", PID, "P4", QTY, 300, P_NAME, "Screw", COLOR, "Red",   WEIGHT, 14.0, P_CITY, "London"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London", PID, "P5", QTY, 400, P_NAME, "Cam",   COLOR, "Blue",  WEIGHT, 12.0, P_CITY, "Paris")
        );
        Relation actual = join(
                join(suppliers(), shipments()),
                rename(parts(), renaming(NAME, P_NAME, CITY, P_CITY))
        );
        assertEquals(expected, actual);
    }
}
