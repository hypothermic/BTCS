package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.TickType;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;



















public class ModLoaderHelper
{
  private static Map<BaseMod, ModLoaderModContainer> notModCallbacks = new HashMap();
  
  public static void updateStandardTicks(BaseMod mod, boolean enable, boolean useClock)
  {
    ModLoaderModContainer mlmc = findOrBuildModContainer(mod);
    BaseModTicker ticker = mlmc.getTickHandler();
    EnumSet<TickType> ticks = ticker.ticks();
    
    if ((enable) && (!useClock)) {
      ticks.add(TickType.RENDER);
    } else {
      ticks.remove(TickType.RENDER);
    }
    
    if ((enable) && ((useClock) || (FMLCommonHandler.instance().getSide().isServer()))) {
      ticks.add(TickType.GAME);
    } else {
      ticks.remove(TickType.GAME);
    }
  }
  
  public static void updateGUITicks(BaseMod mod, boolean enable, boolean useClock)
  {
    ModLoaderModContainer mlmc = findOrBuildModContainer(mod);
    EnumSet<TickType> ticks = mlmc.getTickHandler().ticks();
    
    if ((enable) && (!useClock)) {
      ticks.add(TickType.GUI);
    } else {
      ticks.remove(TickType.GUI);
    }
    
    if ((enable) && ((useClock) || (FMLCommonHandler.instance().getSide().isServer()))) {
      ticks.add(TickType.WORLDGUI);
    } else {
      ticks.remove(TickType.WORLDGUI);
    }
  }
  




  private static ModLoaderModContainer findOrBuildModContainer(BaseMod mod)
  {
    ModLoaderModContainer mlmc = (ModLoaderModContainer)FMLCommonHandler.instance().findContainerFor(mod);
    if (mlmc == null) {
      mlmc = (ModLoaderModContainer)notModCallbacks.get(mod);
      if (mlmc == null) {
        mlmc = new ModLoaderModContainer(mod);
        notModCallbacks.put(mod, mlmc);
      }
    }
    return mlmc;
  }
  
  public static ModLoaderModContainer registerRenderHelper(BaseMod mod) {
    ModLoaderModContainer mlmc = findOrBuildModContainer(mod);
    return mlmc;
  }
  




  public static ModLoaderModContainer registerKeyHelper(BaseMod mod)
  {
    ModLoaderModContainer mlmc = findOrBuildModContainer(mod);
    return mlmc;
  }
}
