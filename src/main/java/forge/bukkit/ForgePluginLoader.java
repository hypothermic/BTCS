package forge.bukkit;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.UnknownDependencyException;

public class ForgePluginLoader
  implements PluginLoader
{
  public Plugin loadPlugin(File file)
    throws InvalidPluginException, UnknownDependencyException
  {
    return null;
  }
  
  public PluginDescriptionFile getPluginDescription(File file)
    throws InvalidDescriptionException
  {
    return null;
  }
  

  public Pattern[] getPluginFileFilters()
  {
    return null;
  }
  

  public Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener listener, Plugin plugin)
  {
    return null;
  }
  
  public void enablePlugin(Plugin plugin) {}
  
  public void disablePlugin(Plugin plugin) {}
}
