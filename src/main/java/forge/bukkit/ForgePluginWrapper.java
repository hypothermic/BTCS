package forge.bukkit;

import com.avaje.ebean.EbeanServer;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.server.FMLBukkitHandler;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;

public class ForgePluginWrapper implements Plugin
{
  private ModContainer wrappedMod;
  private PluginLoader loader;
  
  public ForgePluginWrapper(ModContainer mod, PluginLoader pluginLoader)
  {
    this.wrappedMod = mod;
    this.loader = pluginLoader;
  }
  

  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    return false;
  }
  

  public File getDataFolder()
  {
    return null;
  }
  
  public PluginDescriptionFile getDescription()
  {
    return new PluginDescriptionFile(this.wrappedMod.getName(), "ForgeMod", "DummyMainClass");
  }
  

  public FileConfiguration getConfig()
  {
    return null;
  }
  

  public InputStream getResource(String filename)
  {
    return null;
  }
  




  public void saveConfig() {}
  



  public void saveDefaultConfig() {}
  



  public void saveResource(String resourcePath, boolean replace) {}
  



  public void reloadConfig() {}
  



  public PluginLoader getPluginLoader()
  {
    return this.loader;
  }
  

  public Server getServer()
  {
    return null;
  }
  
  public boolean isEnabled()
  {
    return true;
  }
  




  public void onDisable() {}
  



  public void onLoad() {}
  



  public void onEnable() {}
  



  public boolean isNaggable()
  {
    return false;
  }
  



  public void setNaggable(boolean canNag) {}
  


  public EbeanServer getDatabase()
  {
    return null;
  }
  

  public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
  {
    return null;
  }
  
  public Logger getLogger()
  {
    return FMLBukkitHandler.instance().getMinecraftLogger();
  }
  
  public String getName()
  {
    return this.wrappedMod.getName();
  }
}
