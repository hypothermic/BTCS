package nl.hypothermic.btcs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ConfigurationManager {
	
	private Boolean FIRSTRUN = true;
	private static String PROPS_HEADER = " BTCS Configuration File." + Launcher.LS + " https://github.com/hypothermic/BTCS";
	private static String CFG_NAME = "btcs.cfg";
	private static HashMap<String, String> DEFOPTIONS;
	
	public ConfigurationManager(HashMap<String, String> DEFOPTIONS) {
		this.DEFOPTIONS = DEFOPTIONS;
	}

	public HashMap<String, Object> execute() {
		HashMap<String, Object> OPTIONS = new HashMap();
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
    			return OPTIONS;
    		} catch (Exception x3) {
    			x3.printStackTrace();
    			return null;
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
	} return null; }
	
	protected void updateProperty(String property, String state) {
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
	
	protected String getEnvVariable(String name) {
		Properties envresources = new Properties();
		try {
			envresources.load(this.getClass().getResourceAsStream("btcs.info"));
		} catch (IOException e) {
			throw new RuntimeException("BTCS info file not found.");
		}
		return envresources.getProperty(name);
	}
}
