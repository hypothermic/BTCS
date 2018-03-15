package nl.hypothermic.btcs;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.craftbukkit.CraftServer;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.server.MinecraftServer;

public class Launcher {
	
	public static boolean useJline = true;
    public static boolean useConsole = true;
    public static String LS = System.getProperty("line.separator");
	private static double VERSION;
	private static String VTAG;

	public static void main(final String[] args) {
		// TODO: config and info files. Hardcoded for now.
		VERSION = 1.21;
		VTAG = "ALPHA";
		
		System.out.println(LS + " << BTCS v" + VERSION + "-" + VTAG + " >>" + LS);
		new Thread() {
			public void run() {
				OptionParser parser = new OptionParser() {};
			    OptionSet options = null;
			    try
			    {
			      options = parser.parse(args);
			    } catch (OptionException ex) {
			      Logger.getLogger(this.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
			    }
			    
			    if ((options == null) || (options.has("?"))) {
			      try {
			        parser.printHelpOn(System.out);
			      } catch (IOException ex) {
			        Logger.getLogger(this.getName()).log(Level.SEVERE, null, ex);
			      }
			    } else if (options.has("v")) {
			      System.out.println(CraftServer.class.getPackage().getImplementationVersion());
			    } else {
			      try {
			        useJline = !"jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"));
			        
			        if (options.has("nojline")) {
			          System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
			          System.setProperty("user.language", "en");
			          useJline = false;
			        }
			        
			        if (options.has("noconsole")) {
			          useConsole = false;
			        }
			        
			        net.minecraft.server.MinecraftServer.main(options);
			      } catch (Throwable t) {
			        t.printStackTrace();
			      }
			    }
			}
		}.start();
	}
	private static java.util.List<String> asList(String... params) {
		return java.util.Arrays.asList(params);
	}
}
