package nl.hypothermic.btcs;

import java.io.IOException;

public class XLogger {

	/** Simple method to log stuff to console, to centralize BTCS's logging.
	 * Still need to implement this in the java src.
	 */
	public static void x(int xnum, String place) {
		System.out.println("BTCS: Exception " + xnum + " happened in " + place);
	}
}
