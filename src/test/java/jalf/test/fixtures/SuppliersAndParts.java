package jalf.test.fixtures;

import jalf.AttrName;
import jalf.Relation;

import static jalf.DSL.*;

public class SuppliersAndParts {

    public static final AttrName<String> SID = attr("sid", String.class);
    public static final AttrName<String> NAME = attr("name", String.class);
    public static final AttrName<String> CITY = attr("city", String.class);
    public static final AttrName<Integer> STATUS = attr("status", Integer.class);

    public static final AttrName<String> PID = attr("pid", String.class);
    public static final AttrName<String> COLOR = attr("color", String.class);
    public static final AttrName<String> WEIGHT = attr("weight", String.class);

    public static final AttrName<Integer> QTY = attr("qty", Integer.class);

    public static final AttrName<String> SUPPLIER_ID = attr("supplier_id", String.class);
    public static final AttrName<String> LIVES_IN = attr("lives_in", String.class);

    public static Relation suppliers() {
        return relation(
            tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
            tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris"),
            tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
            tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
            tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
        );
    }

    public static Relation parts() {
        return relation(
            tuple(PID, "P1", NAME, "Nut",   COLOR, "Red",   WEIGHT, 12.0, CITY, "London"),
            tuple(PID, "P2", NAME, "Bolt",  COLOR, "Green", WEIGHT, 17.0, CITY, "Paris"),
            tuple(PID, "P3", NAME, "Screw", COLOR, "Blue",  WEIGHT, 17.0, CITY, "Oslo"),
            tuple(PID, "P4", NAME, "Screw", COLOR, "Red",   WEIGHT, 14.0, CITY, "London"),
            tuple(PID, "P5", NAME, "Cam",   COLOR, "Blue",  WEIGHT, 12.0, CITY, "Paris"),
            tuple(PID, "P6", NAME, "Cog",   COLOR, "Red",   WEIGHT, 19.0, CITY, "London")
        );
    }

    public static Relation shipments() {
        return relation(
            tuple(SID, "S1", PID, "P1", QTY, 300),
            tuple(SID, "S1", PID, "P2", QTY, 200),
            tuple(SID, "S1", PID, "P3", QTY, 400),
            tuple(SID, "S1", PID, "P4", QTY, 200),
            tuple(SID, "S1", PID, "P5", QTY, 100),
            tuple(SID, "S1", PID, "P6", QTY, 100),
            tuple(SID, "S2", PID, "P1", QTY, 300),
            tuple(SID, "S2", PID, "P2", QTY, 400),
            tuple(SID, "S3", PID, "P2", QTY, 200),
            tuple(SID, "S4", PID, "P2", QTY, 200),
            tuple(SID, "S4", PID, "P4", QTY, 300),
            tuple(SID, "S4", PID, "P5", QTY, 400)
        );
    }

}
