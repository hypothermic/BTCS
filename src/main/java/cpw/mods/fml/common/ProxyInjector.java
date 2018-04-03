package cpw.mods.fml.common;

import java.lang.reflect.Field;
import java.util.logging.Logger;


















public class ProxyInjector
{
  private String clientName;
  private String serverName;
  private String bukkitName;
  private Field target;
  
  public ProxyInjector(String clientName, String serverName, String bukkitName, Field target)
  {
    this.clientName = clientName;
    this.serverName = serverName;
    this.bukkitName = bukkitName;
    this.target = target;
  }
  
  public boolean isValidFor(Side type)
  {
    if (type == Side.CLIENT)
    {
      return !this.clientName.isEmpty();
    }
    if (type == Side.SERVER)
    {
      return !this.serverName.isEmpty();
    }
    if (type == Side.BUKKIT)
    {
      return this.bukkitName.isEmpty();
    }
    return false;
  }
  
  public void inject(ModContainer mod, Side side)
  {
    String targetType = side == Side.CLIENT ? this.clientName : this.serverName;
    try
    {
      Object proxy = Class.forName(targetType, false, Loader.instance().getModClassLoader()).newInstance();
      if (this.target.getType().isAssignableFrom(proxy.getClass()))
      {
        this.target.set(mod.getMod(), proxy);
      } else {
        FMLCommonHandler.instance().getFMLLogger().severe(String.format("Attempted to load a proxy type %s into %s, but the types don't match", new Object[] { targetType, this.target.getName() }));
        throw new LoaderException();
      }
    }
    catch (Exception e)
    {
      FMLCommonHandler.instance().getFMLLogger().severe(String.format("An error occured trying to load a proxy type %s into %s", new Object[] { targetType, this.target.getName() }));
      FMLCommonHandler.instance().getFMLLogger().throwing("ProxyInjector", "inject", e);
      throw new LoaderException(e);
    }
  }
}
