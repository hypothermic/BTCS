package net.minecraft.server;

import java.util.Random;

public class WM_ItemCrossbow extends WM_ItemReloadable
{
  public int overrideIDloaded;
  public int overrideIDunloaded;
  
  public WM_ItemCrossbow(int paramInt1, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramEnumToolMaterial, paramWM_EnumWeapon, 1);
    this.overrideIDloaded = paramInt2;
    this.overrideIDunloaded = paramInt3;
  }
  
  protected void onReloadClick(int paramInt, ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramInt == this.reloadClicks)
    {
      paramEntityHuman.C_();
      paramWorld.makeSound(paramEntityHuman, "random.click", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.4F));
      d(this.overrideIDloaded);
    }
  }
  
  public int getReloadClickDelay(int paramInt, ItemStack paramItemStack)
  {
    return 20;
  }
  
  public void fire(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt)
  {
    int i = c(paramItemStack) - paramInt;
    float f = i / 20.0F;
    f = (f * f + f * 2.0F) / 3.0F;
    
    if (f > 1.0F)
    {
      f = 1.0F;
    }
    
    f /= 3.0F;
    f += 0.02F;
    WM_EntityCrossbowBolt localWM_EntityCrossbowBolt = new WM_EntityCrossbowBolt(paramWorld, paramEntityHuman, 0.5F / f);
    
    if (paramEntityHuman.abilities.canInstantlyBuild)
    {
      localWM_EntityCrossbowBolt.doesArrowBelongToPlayer = false;
    }
    
    if (!paramWorld.isStatic)
    {
      paramWorld.addEntity(localWM_EntityCrossbowBolt);
    }
    
    postShootingEffects(paramItemStack, paramEntityHuman, paramWorld, paramEntityHuman.isSneaking());
    resetReload(paramItemStack);
  }
  
  public void resetReload(ItemStack paramItemStack)
  {
    super.resetReload(paramItemStack);
    d(this.overrideIDunloaded);
  }
  
  public int getAmmoItemId()
  {
    return mod_WeaponMod.bolt.id;
  }
  
  protected void postShootingEffects(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, boolean paramBoolean)
  {
    paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    paramEntityHuman.pitch -= (paramBoolean ? 4.0F : 8.0F);
  }
}
