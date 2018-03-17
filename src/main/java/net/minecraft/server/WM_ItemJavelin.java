package net.minecraft.server;

import java.util.Random;

public class WM_ItemJavelin extends WM_ItemWeaponMod
{
  public WM_ItemJavelin(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
    this.maxStackSize = 16;
  }
  



  public void a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt)
  {
    if (paramEntityHuman.inventory.d(this.id))
    {
      int i = c(paramItemStack) - paramInt;
      float f = i / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      
      if (f < 0.1D)
      {
        return;
      }
      
      if (f > 1.0F)
      {
        f = 1.0F;
      }
      
      boolean bool = false;
      
      if ((!paramEntityHuman.onGround) && (!paramEntityHuman.aU()) && (((paramEntityHuman.pitch >= 0.0F) && (paramEntityHuman.motY < 0.0D)) || ((paramEntityHuman.pitch <= 0.0F) && (paramEntityHuman.motY > 0.0D))))
      {
        bool = true;
      }
      
      WM_EntityJavelin localWM_EntityJavelin = new WM_EntityJavelin(paramWorld, paramEntityHuman, f * (1.0F + (bool ? 0.5F : 0.0F)));
      localWM_EntityJavelin.isCritical = bool;
      
      if (paramEntityHuman.inventory.c(this.id))
      {
        paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
        
        if (!paramWorld.isStatic)
        {
          paramWorld.addEntity(localWM_EntityJavelin);
        }
      }
    }
  }
  
  public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    return paramItemStack;
  }
  



  public int c(ItemStack paramItemStack)
  {
    return 72000;
  }
  



  public EnumAnimation d(ItemStack paramItemStack)
  {
    return EnumAnimation.e;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramEntityHuman.inventory.d(this.id))
    {
      paramEntityHuman.a(paramItemStack, c(paramItemStack));
    }
    
    return paramItemStack;
  }
}
