package nl.hypothermic.btcs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.craftbukkit.CraftServer;

import cpw.mods.fml.common.registry.FMLRegistry;
import cpw.mods.fml.server.FMLBukkitHandler;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import net.minecraft.server.MinecraftServer;
import nl.hypothermic.btcs.packetapi.PacketAPI;
import nl.hypothermic.btcs.packetapi.ServerInfoEventListener;

public final class Launcher {
	
	public static boolean useJline = true;
    public static boolean useConsole = true;
    public static String LS;
	private static double VERSION;
	private static String VTAG;
	private static final String PROPS_HEADER = " BTCS+ Configuration File." + LS + " https://github.com/hypothermic/BTCS";
	private static final String CFG_NAME = "btcs.cfg";
	public static final File MC_CFG_FILE = new File("mc.cfg"); /**{@link net.minecraft.server.PropertyManager#PropertyManager(OptionSet)}*/ // TODO: add to config
	public static final String MC_LOG_PATTERN = "mc.log"; /**{@link net.minecraft.server.ConsoleLogManager#init(MinecraftServer)} ln 40.*/ // TODO: add to config
	public static final File BUKKIT_CFG_FILE = new File("bukkit.cfg"); /**{@link org.bukkit.craftbukkit.CraftServer#getConfigFile()}*/ // TODO: add to config
	public static final File BUKKIT_PLUGIN_FOLDER = new File("plugins"); /**{@link org.bukkit.craftbukkit.CraftServer#loadPlugins()}*/ // TODO: add to config
	// I wish Properties files could store booleans .-.
	// I guess we'll have to store them as Strings for now and Boolean.parseBoolean() when we load them
	// TODO: add integrity checker to properties file.
	private static HashMap<String, String> DEFOPTIONS = new HashMap() {{ put("force-jline", "false");
																		 put("deploy-resources", "true");
																		 put("deploy-use-forgeplugin", "false"); // ForgePlugin causes many issues. Disabled by default for now. 
																		 put("enable-update-check", "true");
																		 put("enable-servercontroller", "false");
																		 put("enable-message-login-modlist", "false");
																		 put("enable-debug", "false"); }};
	
	public static boolean FORGE_ANNOUNCE_LOGIN = false; /**{@link forge.PacketHandlerServer#onModListResponse} ln 69.*/
	public static boolean ENABLE_DEBUG = false;
																		 
	private static final ConfigurationManager c = new ConfigurationManager(DEFOPTIONS);
	private static final ResourceManager r = new ResourceManager();

	public static final ConcurrencyManager cc = new ConcurrencyManager();
																		 
	public static final void main(final String[] args) {
		LS = System.getProperty("line.separator");
		// TODO: include these in config file. Hardcoded for now since it's not high priority.
		VERSION = 1.09;
		VTAG = "BETA";
		
		System.out.println(LS + "  << BTCS++ v" + VERSION + "-" + VTAG + " >>" + LS
							  + "  << Java " + getVersion() + " on " + System.getProperty("os.name") + " >>" + LS + LS
							  + " <<  Load Configuration >>");
		final HashMap config = initConfig();
		System.out.println("           Done." + LS);
		if ((Boolean) config.get("enable-update-check")) {
			System.out.println(" << Checking for Update >>");
			if (new UpdateManager().check()) {
				System.out.println(" Updates are availible." + LS);
			} else {
				System.out.println("           Done." + LS);
			}
		}
		// TODO: check if resources exist and deploy them, for now, we let the user choose.
		if ((Boolean) config.get("deploy-resources")) {
			System.out.println(" << Deploying Resources >>");
			initRsc((Boolean) config.get("deploy-use-forgeplugin"));
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
			    	if (options.has("config")) {
			    		throw new RuntimeException("No configuration file specified. Exiting.");
			    	}
			        if (options.has("noconsole")) {
			            useConsole = false;
			        }
			        
			        MinecraftServer mcs = net.minecraft.server.MinecraftServer.main(options);
			        
			        // Prepare ServerController API if enabled
			        if ((Boolean) config.get("enable-servercontroller")) {
			        	ServerController.instance().setEnabled(true);
			        	ServerController.instance().setFMLBukkitHandler(FMLBukkitHandler.instance());
			        	ServerController.instance().setFMLRegistry(FMLRegistry.instance());
			        	ServerController.instance().setMinecraftInstance(mcs);
			        } else {
			        	ServerController.instance().setEnabled(false);
			        }
			        
			        if ((Boolean) config.get("enable-message-login-modlist")) {
			        	FORGE_ANNOUNCE_LOGIN = true;
				    }
			        
			        if ((Boolean) config.get("enable-debug")) {
			        	ENABLE_DEBUG = true;
				    }
			        
			      } catch (Exception x) {
			        x.printStackTrace();
			      }
			    }
			}
		}.start();
	}
	
	private static final java.util.List<String> asList(String... params) {
		return java.util.Arrays.asList(params);
	}
	
	private static final void initRsc(boolean fp) {
		File destination = new File("resources.dat");
		// bool if use forgeplugin or not
		if (fp) {
			r.extract("/resources.dat", destination);
		} else {
			r.extract("/old-resources.dat", destination);
		}
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
	
	private static final HashMap<String, Object> initConfig(){
    	return c.execute();
    }
	
	// getVersion() method is grabbed from StackOverflow: 
	// https://stackoverflow.com/questions/2591083/getting-java-version-at-runtime
	static final double getVersion() {
	    String version = System.getProperty("java.version");
	    int pos = version.indexOf('.');
	    pos = version.indexOf('.', pos+1);
	    return Double.parseDouble (version.substring (0, pos));
	}
	
	public static final double getBTCSVersion() {
		return VERSION;
	}
	
	public static final String getBTCSVersionTag() {
		return VTAG;
	}
}
