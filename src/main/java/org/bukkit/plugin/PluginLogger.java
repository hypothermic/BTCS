package org.bukkit.plugin;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import org.bukkit.Server;







public class PluginLogger
  extends Logger
{
  private String pluginName;
  
  public PluginLogger(Plugin context)
  {
    super(context.getClass().getCanonicalName(), null);
    String prefix = context.getDescription().getPrefix();
    this.pluginName = ("[" + context.getDescription().getName() + "] ");
    setParent(context.getServer().getLogger());
    setLevel(Level.ALL);
  }
  
  public void log(LogRecord logRecord)
  {
    logRecord.setMessage(this.pluginName + logRecord.getMessage());
    super.log(logRecord);
  }
}
