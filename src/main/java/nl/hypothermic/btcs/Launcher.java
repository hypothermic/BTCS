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
import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.MinecraftServer;

public class Launcher {
	
	public static boolean useJline = true;
    public static boolean useConsole = true;
    public static String LS;
	private static double VERSION;
	private static String VTAG;
	private static Boolean FIRSTRUN = true;
	private static HashMap<String, Boolean> OPTIONS = new HashMap();
	private static String PROPS_HEADER = " BTCS Configuration File." + LS + " https://github.com/hypothermic/BTCS";
	private static String CFG_NAME = "btcs.cfg";
	// I wish Properties files could store booleans .-.
	// I guess we'll have to store them as Strings for now and Boolean.parseBoolean() when we load them
	private static HashMap<String, String> DEFOPTIONS = new HashMap() {{ put("force-jline", "false");
																		 put("deploy-resources", "true"); }};

	public static void main(final String[] args) {
		LS = System.getProperty("line.separator");
		// TODO: include these in config file. Hardcoded for now since it's not high priority.
		VERSION = 1.22;
		VTAG = "ALPHA";
		
		System.out.println(LS + " << BTCS v" + VERSION + "-" + VTAG + " >>" + LS);
		initConfig();
		// TODO: check if resources exist and deploy them, for now, we let the user choose.
		if (OPTIONS.get("deploy-resources")) {
			initRsc();
			updateProperty("deploy-resources", "false");
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
			    	  if (!(boolean) OPTIONS.get("force-jline")) {
			        useJline = !"jline.UnsupportedTerminal".equals(System.getProperty("jline.terminal"));
			        
			        if (options.has("nojline")) {
			          System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
			          System.setProperty("user.language", "en");
			          useJline = false;
			        }}
			        
			        if (options.has("noconsole")) {
			          useConsole = false;
			        }
			        
			        Block block = Block.BRICK;
			        System.out.println(block.id);
			        
			        Item test = Item.BOOK;
			        System.out.println(test.id);
			        
			        //net.minecraft.server.MinecraftServer.main(options);
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
	
	private static void initRsc() {
		ResourceManager r = new ResourceManager();
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
	
	private static void initConfig(){
    	File propsExist = new File(CFG_NAME);
    	while (FIRSTRUN) {
    	if (propsExist.exists() && !propsExist.isDirectory()) {
    		FIRSTRUN = false;
    		try {
    			FileReader reader = new FileReader(CFG_NAME);
    			Properties props = new Properties();
    			props.load(reader);
    			for (String key : DEFOPTIONS.keySet()) {
    				OPTIONS.put(key, Boolean.parseBoolean((String) props.getOrDefault(key, DEFOPTIONS.get(key))));
    			}
    		} catch (Exception x3) {
    			x3.printStackTrace();
    		}
    	} else {
    		FileWriter propwrite = null;
    		Properties props = new Properties();
    		for (String key : DEFOPTIONS.keySet()) {
    			props.setProperty(key, (String) DEFOPTIONS.get(key));
    		}
    		try {
    			propwrite = new FileWriter(CFG_NAME);
    			props.store(propwrite, PROPS_HEADER);
    			propwrite.close();
    		} catch (IOException x1) {
    			x1.printStackTrace();
    			System.out.println("BTCS: Exception X300 happened in Launcher: " + x1);
    		} finally {
    			if (propwrite != null) {
    				try {
    					propwrite.close();
    				} catch (Exception x2) {
    					x2.printStackTrace();
    				}
    			}
    		}
    	}
    }}
	
	private static void updateProperty(String property, String state) {
		try {
			FileInputStream in = new FileInputStream(CFG_NAME);
			Properties props = new Properties();
			props.load(in);
			in.close();

			FileOutputStream out = new FileOutputStream(CFG_NAME);
			props.setProperty(property, state);
			props.store(out, PROPS_HEADER);
			out.close();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
