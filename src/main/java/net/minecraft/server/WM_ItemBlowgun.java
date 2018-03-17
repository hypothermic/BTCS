package net.minecraft.server;

import java.util.Random;

public class WM_ItemBlowgun extends WM_ItemReloadable
{
  public WM_ItemBlowgun(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon, 1);
  }
  
  protected void onReloadClick(int paramInt, ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramInt == this.reloadClicks)
    {
      paramEntityHuman.C_();
      paramWorld.makeSound(paramEntityHuman, "random.click", 0.8F, 1.0F / (c.nextFloat() * 0.4F + 0.4F));
    }
  }
  
  public int getReloadClickDelay(int paramInt, ItemStack paramItemStack)
  {
    return 10;
  }
  
  public void fire(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt)
  {
    int i = c(paramItemStack) - paramInt;
    float f = i / 20.0F;
    f = (f * f + f * 2.0F) / 3.0F;
    
    if (f < 0.1F)
    {
      return;
    }
    
    if (f > 1.0F)
    {
      f = 1.0F;
    }
    
    WM_EntityBlowgunDart localWM_EntityBlowgunDart = new WM_EntityBlowgunDart(paramWorld, paramEntityHuman, f * 1.5F);
    
    if (paramEntityHuman.abilities.canInstantlyBuild)
    {
      localWM_EntityBlowgunDart.doesArrowBelongToPlayer = false;
    }
    
    if (!paramWorld.isStatic)
    {
      paramWorld.addEntity(localWM_EntityBlowgunDart);
    }
    
    postShootingEffects(paramItemStack, paramEntityHuman, paramWorld, paramEntityHuman.isSneaking());
    resetReload(paramItemStack);
  }
  
  public void playEmptySound(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (c.nextFloat() * 0.2F + 0.5F));
  }
  
  public void playChargeSound(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    paramWorld.makeSound(paramEntityHuman, "random.breath", 1.0F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
  }
  
  public int getAmmoItemId()
  {
    return mod_WeaponMod.dart.id;
  }
  
  protected void postShootingEffects(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, boolean paramBoolean)
  {
    paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (c.nextFloat() * 0.2F + 0.5F));
    float f1 = -MathHelper.sin((paramEntityHuman.yaw + 23.0F) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    float f2 = -MathHelper.sin(paramEntityHuman.pitch / 180.0F * 3.1415927F) - 0.1F;
    float f3 = MathHelper.cos((paramEntityHuman.yaw + 23.0F) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    paramWorld.a("explode", paramEntityHuman.locX + f1, paramEntityHuman.locY + f2, paramEntityHuman.locZ + f3, 0.0D, 0.0D, 0.0D);
  }
}
