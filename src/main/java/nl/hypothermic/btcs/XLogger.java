package nl.hypothermic.btcs;

import java.io.IOException;

/** Class to centralize BTCS's logging, so it's easy to disable it. */

public final class XLogger {

	/** Log to System.out */
	public static void generic(String message) {
		System.out.println("BTCS: " + message);
	}
	
	/** Log to System.err */
	public static void generr(String message) {
		System.err.println("BTCS: " + message);
	}
	
	/** Log critical to System.err */
	public static void gencrit(String message) {
		System.err.println("[CRITICAL] BTCS: " + message);
	}
	
	/** Debug message */
	public static void debug(String message) {
		if (Launcher.ENABLE_DEBUG == true) System.out.println(message);
	}
	
	/** Object debugging passthrough, for testing if object is null within super()
	 * @author hypothermic
	 */
	public static <T> T genpass(T object) {
		debug("Received object in passthrough.");
		if (object == null) {
			gencrit("Object is null!");
		}
		if (object instanceof org.bukkit.World) {
			debug("Passthrough object is a World.");
		} else if (object instanceof org.bukkit.Chunk) {
			debug("Passthrough object is a org.bukkit.Chunk.");
		} else if (object instanceof Integer) {
			generr("Passthrough object is an Integer: " + object.toString());
		} else if (object instanceof org.bukkit.Material) {
			try {
				((org.bukkit.Material) object).name();
			} catch (NullPointerException npe) {
				gencrit("Passthrough: NPE when retrieving Material name for " + ((org.bukkit.Material) object).getId());
			}
			generr("Passthrough object is a Material " + ((org.bukkit.Material) object).name());
		}
		return object;
	}
	
	/*public static String getCallerClassName() { 
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(XLogger.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClassName();
            }
        }
        return null;
     }*/
}
