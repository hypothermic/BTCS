package nl.hypothermic.btcs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class UpdateManager {
	
	/**
	 * BTCS's update manager.
	 * Should only be enabled when user agrees.
	 * TODO: auto updates
	 */

	public UpdateManager() {}
	
	/**
	 * Returns true if there are updates availible.
	 * URL is hardcoded, but because the source code is open,
	 * anyone can edit it if the hypothermic servers go down.
	 */
	
	public final boolean check() {
		BufferedReader in = null;
		try {
			URL url = new URL("https://hypothermic.nl/btcs/latest.htm");
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String body = in.readLine();
			if (!(body.contains("" + Launcher.getBTCSVersion()))) {
				System.out.println("      Updates are availible.");
			}
		} catch (IOException x) {
			System.out.println("  Could not reach update server.");
		} finally {
			if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
		}
		return false;
	}
}
