package nl.hypothermic.btcs;

public class ConcurrencyManager {
	
	public static final NBTReadLimiter rl = new NBTReadLimiter(8);

	public ConcurrencyManager() {
		
	}
}
