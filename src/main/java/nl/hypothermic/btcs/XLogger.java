package nl.hypothermic.btcs;

import java.io.IOException;

public class XLogger {

	/** Simple method to log exception with code to console, to centralize BTCS's logging.
	 * We need to centralize everything so we can switch it off if needed.
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
	
	public static void gencrit(String message) {
		System.err.println("[CRITICAL] BTCS: " + message);
		Launcher.forcestop();
	}
	
	public static void debug(String message) {
		if (Launcher.ENABLE_DEBUG == true) System.out.println(message);
	}
	
	/** Object debugging passthrough, for testing if object is null within super()
	 * @author hypothermic
	 */
	public static <T> T genpass(T object) {
		generic("Received object in passthrough.");
		if (object == null) {
			gencrit("Object is null!");
		}
		if (object instanceof org.bukkit.World) {
			generic("Passthrough object is a World.");
		} else if (object instanceof org.bukkit.Chunk) {
			generic("Passthrough object is a org.bukkit.Chunk.");
		}
		return object;
	}
	
	public static String getCallerClassName() { 
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(XLogger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
     }
}
