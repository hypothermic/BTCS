package net.minecraft.server;

import java.util.Random;

public class WM_ItemKnife extends WM_ItemWeaponMod
{
  private EnumToolMaterial enumToolMaterial;
  
  public WM_ItemKnife(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
    this.enumToolMaterial = paramEnumToolMaterial;
  }
  



  public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (!mod_WeaponMod.instance.properties.canThrowKnife)
    {
      return super.a(paramItemStack, paramWorld, paramEntityHuman);
    }
    
    paramWorld.makeSound(paramEntityHuman, "random.bow", 1.0F, 1.0F / (c.nextFloat() * 0.4F + 0.8F));
    
    if (!paramWorld.isStatic)
    {
      WM_EntityKnife localWM_EntityKnife = new WM_EntityKnife(paramWorld, paramEntityHuman, paramItemStack);
      
      if (paramEntityHuman.abilities.canInstantlyBuild)
      {
        localWM_EntityKnife.doesArrowBelongToPlayer = false;
      }
      
      paramWorld.addEntity(localWM_EntityKnife);
    }
    
    if (paramEntityHuman.abilities.canInstantlyBuild)
    {
      return paramItemStack;
    }
    

    return new ItemStack(this.id, 0, 0);
  }
  




  public EnumAnimation d(ItemStack paramItemStack)
  {
    return mod_WeaponMod.instance.properties.canThrowKnife ? EnumAnimation.a : EnumAnimation.d;
  }
  




  public float getDestroySpeed(ItemStack paramItemStack, Block paramBlock)
  {
    return this.strVsBlock * (paramBlock.id != Block.WEB.id ? 1.0F : 10.0F);
  }
  



  public boolean canDestroySpecialBlock(Block paramBlock)
  {
    return paramBlock.id == Block.WEB.id;
  }
}
