package net.minecraft.server;

import java.util.Random;

public class WM_ItemSpear extends WM_ItemWeaponMod
{
  private EnumToolMaterial enumToolMaterial;
  
  public WM_ItemSpear(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
    this.enumToolMaterial = paramEnumToolMaterial;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!mod_WeaponMod.instance.properties.canThrowSpear)
    {
      return super.a(paramItemStack, paramWorld, paramEntityHuman);
    }
    
    paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    
    if (!paramWorld.isStatic)
    {
      WM_EntitySpear localWM_EntitySpear = new WM_EntitySpear(paramWorld, paramEntityHuman, paramItemStack);
      
      if (paramEntityHuman.abilities.canInstantlyBuild)
      {
        localWM_EntitySpear.doesArrowBelongToPlayer = false;
      }
      
      paramWorld.addEntity(localWM_EntitySpear);
    }
    
    if (paramEntityHuman.abilities.canInstantlyBuild)
    {
      return paramItemStack;
    }
    

    return new ItemStack(this.id, 0, 0);
  }
  




  public EnumAnimation d(ItemStack paramItemStack)
  {
    return mod_WeaponMod.instance.properties.canThrowSpear ? EnumAnimation.a : EnumAnimation.d;
  }
}
