package net.minecraft.server;

import java.util.Collection;
import java.util.Map;

public class WM_ReloadProgress
{
  public boolean isReloading;
  public boolean reloaded;
  public int progress;
  protected ItemStack itemStack;
  private static Map itemStacks = new java.util.HashMap();
  
  protected WM_ReloadProgress(ItemStack paramItemStack)
  {
    this.itemStack = paramItemStack;
    resetReload();
  }
  
  public void resetReload()
  {
    this.progress = 0;
    this.reloaded = false;
    this.isReloading = false;
  }
  
  public static WM_ReloadProgress getReloadProgress(ItemStack paramItemStack)
  {
    if ((paramItemStack == null) || (paramItemStack.count <= 0))
    {
      return null;
    }
    return (WM_ReloadProgress)itemStacks.get(paramItemStack);
  }
  
  public static WM_ReloadProgress registerItemStack(ItemStack paramItemStack)
  {
    WM_ReloadProgress localWM_ReloadProgress = getReloadProgress(paramItemStack);
    if (localWM_ReloadProgress != null)
    {
      cleanList();
      return localWM_ReloadProgress;
    }
    
    localWM_ReloadProgress = new WM_ReloadProgress(paramItemStack);
    itemStacks.put(paramItemStack, localWM_ReloadProgress);
    cleanList();
    return localWM_ReloadProgress;
  }
  
  public static void cleanList()
  {
    ItemStack[] arrayOfItemStack = (ItemStack[])itemStacks.keySet().toArray(new ItemStack[0]);
    for (Object localObject3 : arrayOfItemStack)
    {
      if ((localObject3 == null) || (((ItemStack)localObject3).count <= 0))
      {
        itemStacks.remove(localObject3);
      }
    }
    Object[] x = (WM_ReloadProgress[])itemStacks.values().toArray(new WM_ReloadProgress[0]);
    for (Object localObject4 : x)
    {
      if ((!((WM_ReloadProgress)localObject4).reloaded) && (!((WM_ReloadProgress)localObject4).isReloading))
      {
        ((WM_ReloadProgress)localObject4).resetReload();
      }
    }
  }
}
