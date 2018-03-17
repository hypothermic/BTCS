package nl.hypothermic.btcs;

import java.io.IOException;

public class XLogger {

	/** Simple method to log exception with code to console, to centralize BTCS's logging.
	 * Still need to implement this in the java src.
	 */
	public static void x(int xnum, String place) {
		System.err.println("BTCS: Exception " + xnum + " happened in " + place);
	}
	
	public static void generic(String message) {
		System.out.println("BTCS: " + message);
	}
	
	public static void generr(String message) {
		System.err.println("BTCS: " + message);
	}
}
