package nl.hypothermic.btcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.craftbukkit.CraftServer;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.server.Achievement;
import net.minecraft.server.AchievementList;
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.MinecraftServer;

public class Launcher {
	
	public static boolean useJline = true;
    public static boolean useConsole = true;
    public static String LS;
	private static double VERSION;
	private static String VTAG;
	public static final boolean ENABLE_DEBUG = false;
	private static String PROPS_HEADER = " BTCS Configuration File." + LS + " https://github.com/hypothermic/BTCS";
	private static final String CFG_NAME = "btcs.cfg";
	public static final File MC_CFG_FILE = new File("mc.cfg"); /**{@link net.minecraft.server.PropertyManager#PropertyManager(OptionSet)}*/ // TODO: add to config
	public static final File BUKKIT_CFG_FILE = new File("bukkit.cfg"); /**{@link org.bukkit.craftbukkit.CraftServer#getConfigFile()}*/ // TODO: add to config
	public static final File BUKKIT_PLUGIN_FOLDER = new File("plugins"); /**{@link org.bukkit.craftbukkit.CraftServer#loadPlugins()}*/ // TODO: add to config
	// I wish Properties files could store booleans .-.
	// I guess we'll have to store them as Strings for now and Boolean.parseBoolean() when we load them
	private static HashMap<String, String> DEFOPTIONS = new HashMap() {{ put("force-jline", "false");
																		 put("deploy-resources", "true"); }};
																		 
	private static ConfigurationManager c = new ConfigurationManager(DEFOPTIONS);
	private static ResourceManager r = new ResourceManager();

	private static ConcurrencyManager cc = new ConcurrencyManager();
																		 
	public static void main(final String[] args) {
		LS = System.getProperty("line.separator");
		// TODO: include these in config file. Hardcoded for now since it's not high priority.
		VERSION = 1.00;
		VTAG = "ALPHA";
		
		System.out.println(LS + "  << BTCS+ v" + VERSION + "-" + VTAG + " >>" + LS
							  + "  << Java " + getVersion() + " on " + System.getProperty("os.name") + " >>" + LS + LS
							  + " <<  Load Configuration >>");
		final HashMap config = initConfig();
		System.out.println("           Done." + LS);
		// TODO: check if resources exist and deploy them, for now, we let the user choose.
		if ((Boolean) config.get("deploy-resources")) {
			System.out.println(" << Deploying Resources >>");
			initRsc();
			c.updateProperty("deploy-resources", "false");
			System.out.println("           Done." + LS);
		}
		new Thread() {
			public void run() {
				OptionParser parser = new OptionParser() {};
			    OptionSet options = null;
			    try {
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
			    	  if (!(Boolean) config.get("force-jline")) {
			        useJline = !"jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"));
			        
			        if (options.has("nojline")) {
			          System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
			          System.setProperty("user.language", "en");
			          useJline = false;
			        }}
			    	
			    	// TODO: move all options.has() checks to a seperate class
			    	if (options.has("config")) { // Fun fact: Minecraft crashes if config is not specified.
			    		XLogger.generr("No configuration file specified. Exiting.");
			    		forcestop();
			    	}
			        if (options.has("noconsole")) {
			          useConsole = false;
			        }
			        
			        net.minecraft.server.MinecraftServer.main(options);
			      } catch (Exception x) {
			        x.printStackTrace();
			      }
			    }
			}
		}.start();
	}
	
	private static java.util.List<String> asList(String... params) {
		return java.util.Arrays.asList(params);
	}
	
	private static void initRsc() {
		File destination = new File("resources.dat");
		r.extract("/resources.dat", destination);
		try {
			r.unzip(destination);
		} catch (FileNotFoundException e) {
			System.err.println("BTCS: Resources file could not be unzipped:" + LS);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("BTCS: An error occurred while unzipping resources:" + LS);
			e.printStackTrace();
		}
		destination.delete();
	}
	
	private static HashMap<String, Object> initConfig(){
    	return c.execute();
    }
	
	// getVersion() method is grabbed from StackOverflow: 
	// https://stackoverflow.com/questions/2591083/getting-java-version-at-runtime
	static double getVersion () {
	    String version = System.getProperty("java.version");
	    int pos = version.indexOf('.');
	    pos = version.indexOf('.', pos+1);
	    return Double.parseDouble (version.substring (0, pos));
	}
	
	public static void forcestop() { // public for now ??
		XLogger.generr("Force stop got initiated.");
		System.exit(1);
	}
	
	public static double getBTCSVersion() {
		return VERSION;
	}
	
	public static String getBTCSVersionTag() {
		return VTAG;
	}
}
