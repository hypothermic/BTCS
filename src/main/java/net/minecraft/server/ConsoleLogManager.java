package net.minecraft.server;

import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import joptsimple.OptionSet;
import org.bukkit.craftbukkit.util.TerminalConsoleHandler;

public class ConsoleLogManager
{
  public static Logger a = Logger.getLogger("Minecraft");
  public static Logger global = Logger.getLogger("");
  


  public static void init(MinecraftServer server)
  {
    ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter(server.options.has("log-strip-color"));
    
    a.setUseParentHandlers(false);
    
    ConsoleHandler consolehandler = new TerminalConsoleHandler(server.reader);
    
    for (java.util.logging.Handler handler : global.getHandlers()) {
      global.removeHandler(handler);
    }
    
    consolehandler.setFormatter(new org.bukkit.craftbukkit.util.ShortConsoleLogFormatter(server));
    global.addHandler(consolehandler);
    

    a.addHandler(consolehandler);
    
    try
    {
      String pattern = (String)server.options.valueOf("log-pattern");
      

      String tmpDir = System.getProperty("java.io.tmpdir");
      String homeDir = System.getProperty("user.home");
      if (tmpDir == null) {
        tmpDir = homeDir;
      }
      

      File parent = new File(pattern).getParentFile();
      StringBuilder fixedPattern = new StringBuilder();
      String parentPath = "";
      if (parent != null) {
        parentPath = parent.getPath();
      }
      
      int i = 0;
      while (i < parentPath.length()) {
        char ch = parentPath.charAt(i);
        char ch2 = '\000';
        if (i + 1 < parentPath.length()) {
          ch2 = Character.toLowerCase(pattern.charAt(i + 1));
        }
        
        if (ch == '%') {
          if (ch2 == 'h') {
            i += 2;
            fixedPattern.append(homeDir);
            continue; }
          if (ch2 == 't') {
            i += 2;
            fixedPattern.append(tmpDir);
            continue; }
          if (ch2 == '%')
          {
            i += 2;
            fixedPattern.append("%%");
            continue; }
          if (ch2 != 0) {
            throw new java.io.IOException("log-pattern can only use %t and %h for directories, got %" + ch2);
          }
        }
        
        fixedPattern.append(ch);
        i++;
      }
      

      parent = new File(fixedPattern.toString());
      if (parent != null) {
        parent.mkdirs();
      }
      
      int limit = ((Integer)server.options.valueOf("log-limit")).intValue();
      int count = ((Integer)server.options.valueOf("log-count")).intValue();
      boolean append = ((Boolean)server.options.valueOf("log-append")).booleanValue();
      FileHandler filehandler = new FileHandler(pattern, limit, count, append);
      

      filehandler.setFormatter(consolelogformatter);
      a.addHandler(filehandler);
      global.addHandler(filehandler);
    } catch (Exception exception) {
      a.log(java.util.logging.Level.WARNING, "Failed to log to server.log", exception);
    }
  }
}
