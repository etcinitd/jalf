package jalf.test.fixtures;

import static jalf.util.Utils.relation;
import static jalf.util.Utils.tuple;
import jalf.Relation;

public class SuppliersAndParts {

	public static final String SID = "sid";
	public static final String NAME = "name";
	public static final String CITY = "city";
	public static final String STATUS = "status";

	public static final String PID = "pid";
	public static final String COLOR = "color";
	public static final String WEIGHT = "weight";

	public static final String QTY = "qty";

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
