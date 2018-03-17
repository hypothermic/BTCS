package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import joptsimple.OptionSet;


public class PropertyManager {
  public static Logger a = Logger.getLogger("Minecraft");
  public Properties properties = new Properties();
  private File c;
  
  public PropertyManager(File file1) {
    this.c = file1;
    try { // BTCS: added try-catch
    if (file1.exists()) {
      try {
        this.properties.load(new FileInputStream(file1));
      } catch (Exception exception) {
        a.log(Level.WARNING, "Failed to load " + file1, exception);
        a();
      }
    } else {
      a.log(Level.WARNING, file1 + " does not exist");
      a();
    }} catch (NullPointerException npe) {
    	try {
    		a();
    	} catch (Exception x) {
    		nl.hypothermic.btcs.XLogger.generic("Properties file does not exist and can not be created!");
    	}
    }
  }
  

  private OptionSet options = null;
  
  public PropertyManager(OptionSet options) {
	// BTCS start
    //this((File)options.valueOf("config"));
	this(nl.hypothermic.btcs.Launcher.MC_CFG_FILE);
	// BTCS end
    
    this.options = options;
  }
  
  private <T> T getOverride(String name, T value) {
    if ((this.options != null) && (this.options.has(name))) {
      return (T)this.options.valueOf(name);
    }
    
    return value;
  }
  
  public void a()
  {
    a.log(Level.INFO, "Generating new properties file");
    savePropertiesFile();
  }
  
  public void savePropertiesFile() {
    try {
      this.properties.store(new FileOutputStream(this.c), "Minecraft server properties");
    } catch (Exception exception) {
      a.log(Level.WARNING, "Failed to save " + this.c, exception);
      nl.hypothermic.btcs.Launcher.forcestop();
    }
  }
  
  public File c() {
    return this.c;
  }
  
  public String getString(String s, String s1) {
    if (!this.properties.containsKey(s)) {
      s1 = (String)getOverride(s, s1);
      this.properties.setProperty(s, s1);
      savePropertiesFile();
    }
    
    return (String)getOverride(s, this.properties.getProperty(s, s1));
  }
  
  public int getInt(String s, int i) {
    try {
      return ((Integer)getOverride(s, Integer.valueOf(Integer.parseInt(getString(s, "" + i))))).intValue();
    } catch (Exception exception) {
      i = ((Integer)getOverride(s, Integer.valueOf(i))).intValue();
      this.properties.setProperty(s, "" + i); }
    return i;
  }
  
  public boolean getBoolean(String s, boolean flag)
  {
    try {
      return ((Boolean)getOverride(s, Boolean.valueOf(Boolean.parseBoolean(getString(s, "" + flag))))).booleanValue();
    } catch (Exception exception) {
      flag = ((Boolean)getOverride(s, Boolean.valueOf(flag))).booleanValue();
      this.properties.setProperty(s, "" + flag); }
    return flag;
  }
  
  public void a(String s, Object object)
  {
    this.properties.setProperty(s, "" + object);
  }
  
  public void setBoolean(String s, boolean flag) {
    this.properties.setProperty(s, "" + flag);
    savePropertiesFile();
  }
}
