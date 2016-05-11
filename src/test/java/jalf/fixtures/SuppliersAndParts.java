package jalf.fixtures;

import static jalf.DSL.attr;
import static jalf.DSL.heading;
import static jalf.DSL.key;
import static jalf.DSL.keys;
import static jalf.DSL.relation;
import static jalf.DSL.tuple;
import jalf.AttrName;
import jalf.Relation;

public class SuppliersAndParts {

    public static final AttrName SID = attr("sid");
    public static final AttrName NAME = attr("name");
    public static final AttrName CITY = attr("city");
    public static final AttrName STATUS = attr("status");

    public static final AttrName PID = attr("pid");
    public static final AttrName COLOR = attr("color");
    public static final AttrName WEIGHT = attr("weight");

    public static final AttrName QTY = attr("qty");

    public static final AttrName SUPPLIER_ID = attr("supplier_id");
    public static final AttrName LIVES_IN = attr("lives_in");

    public static final AttrName P_NAME = attr("p_name");
    public static final AttrName P_CITY = attr("p_city");

    public static Relation suppliers() {
        return relation(
                heading(SID, String.class, NAME, String.class, STATUS, Integer.class, CITY, String.class),
                keys(key(SID)),
                tuple(SID, "S1", NAME, "Smith", STATUS, 20, CITY, "London"),
                tuple(SID, "S2", NAME, "Jones", STATUS, 10, CITY, "Paris"),
                tuple(SID, "S3", NAME, "Blake", STATUS, 30, CITY, "Paris"),
                tuple(SID, "S4", NAME, "Clark", STATUS, 20, CITY, "London"),
                tuple(SID, "S5", NAME, "Adams", STATUS, 30, CITY, "Athens")
                );
    }

    public static Relation parts() {
        return relation(
                heading(PID, String.class, NAME, String.class, COLOR, String.class, WEIGHT, Double.class, CITY, String.class),
                keys(key(PID)),
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
                heading(SID, String.class, PID, String.class, QTY, Integer.class),
                keys(key(SID, PID)),
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
