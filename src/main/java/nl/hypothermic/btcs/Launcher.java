package nl.hypothermic.btcs;

import net.minecraft.server.MinecraftServer;

public class Launcher {
	
	private static double VERSION;
	private static String VTAG;

	public static void main(String[] args) {
		VERSION = 1.20;
		VTAG = "BETA";
		System.out.println("<< BTCS v" + VERSION + VTAG + " >>");
		new Thread() {
			public void run() {
				MinecraftServer.main(null);
			}
		}.start();
	}
}
