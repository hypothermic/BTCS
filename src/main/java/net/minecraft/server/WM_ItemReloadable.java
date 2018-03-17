package net.minecraft.server;

public abstract class WM_ItemReloadable extends WM_ItemWeaponMod
{
  protected int clickDelay;
  protected int reloadClicks;
  protected static final int MAX_DELAY = 72000;
  
  protected WM_ItemReloadable(int paramInt1, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon, int paramInt2)
  {
    super(paramInt1, paramEnumToolMaterial, paramWM_EnumWeapon);
    this.clickDelay = 20;
    this.reloadClicks = paramInt2;
    this.clickDelay = 72000;
  }
  
  protected final void cycleReloadProgress(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    WM_ReloadProgress localWM_ReloadProgress = WM_ReloadProgress.getReloadProgress(paramItemStack);
    onReloadClick(localWM_ReloadProgress.progress, paramItemStack, paramWorld, paramEntityHuman);
    
    if (localWM_ReloadProgress.progress >= this.reloadClicks)
    {
      localWM_ReloadProgress.reloaded = true;
      this.clickDelay = 72000;
    }
    else
    {
      this.clickDelay = getReloadClickDelay(localWM_ReloadProgress.progress, paramItemStack);
      localWM_ReloadProgress.progress += 1;
    }
  }
  
  protected abstract void onReloadClick(int paramInt, ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman);
  
  public abstract int getReloadClickDelay(int paramInt, ItemStack paramItemStack);
  
  public abstract int getAmmoItemId();
  
  public abstract void fire(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt);
  
  public void resetReload(ItemStack paramItemStack)
  {
    WM_ReloadProgress localWM_ReloadProgress = WM_ReloadProgress.getReloadProgress(paramItemStack);
    localWM_ReloadProgress.resetReload();
    this.clickDelay = getReloadClickDelay(localWM_ReloadProgress.progress, paramItemStack);
  }
  



  public final void a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt)
  {
    WM_ReloadProgress localWM_ReloadProgress = WM_ReloadProgress.getReloadProgress(paramItemStack);
    
    if (localWM_ReloadProgress == null)
    {
      return;
    }
    
    if (!localWM_ReloadProgress.reloaded)
    {
      resetReload(paramItemStack);
    }
    
    if (localWM_ReloadProgress.reloaded)
    {
      if (localWM_ReloadProgress.isReloading)
      {
        localWM_ReloadProgress.isReloading = false;
      }
      else
      {
        if ((!paramEntityHuman.abilities.canInstantlyBuild) && (!paramEntityHuman.inventory.c(getAmmoItemId())))
        {
          resetReload(paramItemStack);
          return;
        }
        
        fire(paramItemStack, paramWorld, paramEntityHuman, paramInt);
      }
    }
  }
  



  public final int c(ItemStack paramItemStack)
  {
    return this.clickDelay;
  }
  



  public EnumAnimation d(ItemStack paramItemStack)
  {
    WM_ReloadProgress localWM_ReloadProgress = WM_ReloadProgress.getReloadProgress(paramItemStack);
    return (localWM_ReloadProgress == null) || (!localWM_ReloadProgress.isReloading) ? EnumAnimation.e : EnumAnimation.d;
  }
  



  public final ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramItemStack.count <= 0)
    {
      return paramItemStack;
    }
    
    WM_ReloadProgress localWM_ReloadProgress = WM_ReloadProgress.registerItemStack(paramItemStack);
    
    if (localWM_ReloadProgress == null)
    {
      return paramItemStack;
    }
    
    if (!localWM_ReloadProgress.isReloading)
    {
      if (localWM_ReloadProgress.reloaded)
      {
        playChargeSound(paramItemStack, paramWorld, paramEntityHuman);
        paramEntityHuman.a(paramItemStack, c(paramItemStack));
      }
      else
      {
        localWM_ReloadProgress.isReloading = true;
      }
    }
    else if (localWM_ReloadProgress.reloaded)
    {
      localWM_ReloadProgress.resetReload();
    }
    
    if ((localWM_ReloadProgress.isReloading) && (!localWM_ReloadProgress.reloaded))
    {
      if ((paramEntityHuman.abilities.canInstantlyBuild) || (paramEntityHuman.inventory.d(getAmmoItemId())))
      {
        cycleReloadProgress(paramItemStack, paramWorld, paramEntityHuman);
        paramEntityHuman.a(paramItemStack, c(paramItemStack));
      }
      else
      {
        playEmptySound(paramItemStack, paramWorld, paramEntityHuman);
        resetReload(paramItemStack);
      }
    }
    
    return paramItemStack;
  }
  
  public void playEmptySound(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramWorld.makeSound(paramEntityHuman, "random.click", 1.0F, 1.25F);
  }
  
  public void playChargeSound(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {}
  
  protected void postShootingEffects(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, boolean paramBoolean) {}
}
