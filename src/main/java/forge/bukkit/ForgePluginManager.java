package forge.bukkit;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;







public class ForgePluginManager
  extends SimplePluginManager
{
  private ForgePluginLoader forgePluginLoader;
  private Map<File, ForgePluginWrapper> wraps;
  
  public ForgePluginManager(Server instance, SimpleCommandMap commandMap)
  {
    super(instance, commandMap);
    this.forgePluginLoader = new ForgePluginLoader();
  }
  
  protected Map<String, File> prePopulatePluginList()
  {
    Map<String, File> parent = super.prePopulatePluginList();
    this.wraps = new HashMap();
    for (ModContainer mod : Loader.getModList()) {
      ForgePluginWrapper wrapper = new ForgePluginWrapper(mod, this.forgePluginLoader);
      File dummy = new File(mod.getName());
      parent.put(wrapper.getName(), dummy);
      this.wraps.put(dummy, wrapper);
    }
    return parent;
  }
  
  public synchronized Plugin loadPlugin(File file) throws InvalidPluginException, UnknownDependencyException
  {
    if (this.wraps.containsKey(file)) {
      ForgePluginWrapper wrapper = (ForgePluginWrapper)this.wraps.get(file);
      this.plugins.add(wrapper);
      this.lookupNames.put(wrapper.getName(), wrapper);
      return wrapper;
    }
    return super.loadPlugin(file);
  }
}
