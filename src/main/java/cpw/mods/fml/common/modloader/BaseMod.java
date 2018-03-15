package cpw.mods.fml.common.modloader;

import cpw.mods.fml.common.IConsoleHandler;
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.IDispenseHandler;
import cpw.mods.fml.common.INetworkHandler;
import cpw.mods.fml.common.IPickupNotifier;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.TickType;
import java.util.Map;

public abstract interface BaseMod
  extends IWorldGenerator, IPickupNotifier, IDispenseHandler, ICraftingHandler, INetworkHandler, IConsoleHandler, IPlayerTracker
{
  public abstract void modsLoaded();
  
  public abstract void load();
  
  public abstract boolean doTickInGame(TickType paramTickType, boolean paramBoolean, Object paramObject, Object... paramVarArgs);
  
  public abstract String getName();
  
  public abstract String getPriorities();
  
  public abstract int addFuel(int paramInt1, int paramInt2);
  
  public abstract void onRenderHarvest(Map paramMap);
  
  public abstract void onRegisterAnimations();
  
  public abstract String getVersion();
  
  public abstract void keyBindingEvent(Object paramObject);
}
