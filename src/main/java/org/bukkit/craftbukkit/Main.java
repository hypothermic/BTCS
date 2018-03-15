package org.bukkit.craftbukkit;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;

public class Main
{
  public static boolean useJline = true;
  public static boolean useConsole = true;
  
  public static void main(String[] args)
  {
    OptionParser parser = new OptionParser() {};
    OptionSet options = null;
    try
    {
      options = parser.parse(args);
    } catch (OptionException ex) {
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
    }
    
    if ((options == null) || (options.has("?"))) {
      try {
        parser.printHelpOn(System.out);
      } catch (IOException ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
  
  private static java.util.List<String> asList(String... params) {
    return java.util.Arrays.asList(params);
  }
}
