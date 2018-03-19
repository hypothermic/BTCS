package nl.hypothermic.btcs;

public class ForgeExchanger {

	public ForgeExchanger() {
		
	}
	
	public static class Reporter {
		public void reportModLoaded(String modName) {
			System.out.println("BTCS - Forge: Successfully loaded mod: " + modName);
			// do whatever the fuck you want with this information
		}
		// TODO: more reports
	}
}
