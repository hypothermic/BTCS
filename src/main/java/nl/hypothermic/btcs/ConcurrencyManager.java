package nl.hypothermic.btcs;

import java.util.HashMap;

public class ConcurrencyManager {
	
	public static final NBTReadLimiter rl = new NBTReadLimiter(8);
	
	public static final HashMap<String, Integer> viewdist = new HashMap<String, Integer>();

	public ConcurrencyManager() {
		
	}
}
