package net.minecraft.server;

import java.util.Random;

public class WM_ItemBlunderbuss extends WM_ItemReloadable
{
  public WM_ItemBlunderbuss(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon, 1);
  }
  
  protected void onReloadClick(int paramInt, ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramInt == this.reloadClicks)
    {
      paramEntityHuman.C_();
      paramWorld.makeSound(paramEntityHuman, "random.door_close", 0.8F, 1.0F / (c.nextFloat() * 0.2F + 0.0F));
    }
  }
  
  public int getReloadClickDelay(int paramInt, ItemStack paramItemStack)
  {
    return 20;
  }
  
  public void fire(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt)
  {
    if (!paramWorld.isStatic)
    {
      WM_EntityBlunderShot.fireSpreadShot(paramWorld, paramEntityHuman);
    }
    
    int i = 4;
    
    if (paramItemStack.getData() + i <= paramItemStack.i())
    {
      postShootingEffects(paramItemStack, paramEntityHuman, paramWorld, paramEntityHuman.isSneaking());
      resetReload(paramItemStack);
    }
    
    paramItemStack.damage(i, paramEntityHuman);
  }
  
  public int getAmmoItemId()
  {
    return mod_WeaponMod.blunderShot.id;
  }
  
  protected void postShootingEffects(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, boolean paramBoolean)
  {
    paramWorld.makeSound(paramEntityHuman, "random.old_explode", 3.0F, 1.0F / (c.nextFloat() * 0.4F + 1.0F));
    float f1 = paramBoolean ? -0.1F : -0.2F;
    double d1 = -MathHelper.sin(paramEntityHuman.yaw / 180.0F * 3.1415927F) * MathHelper.cos(0.0F) * f1;
    double d2 = MathHelper.cos(paramEntityHuman.yaw / 180.0F * 3.1415927F) * MathHelper.cos(0.0F) * f1;
    paramEntityHuman.pitch -= (paramBoolean ? 17.5F : 25.0F);
    paramEntityHuman.b_(d1, 0.0D, d2);
    float f2 = -MathHelper.sin((paramEntityHuman.yaw + 23.0F) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    float f3 = -MathHelper.sin(paramEntityHuman.pitch / 180.0F * 3.1415927F) - 0.1F;
    float f4 = MathHelper.cos((paramEntityHuman.yaw + 23.0F) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntityHuman.pitch / 180.0F * 3.1415927F);
    
    for (int i = 0; i < 3; i++)
    {
      paramWorld.a("smoke", paramEntityHuman.locX + f2, paramEntityHuman.locY + f3, paramEntityHuman.locZ + f4, 0.0D, 0.0D, 0.0D);
    }
    
    paramWorld.a("flame", paramEntityHuman.locX + f2, paramEntityHuman.locY + f3, paramEntityHuman.locZ + f4, 0.0D, 0.0D, 0.0D);
  }
}
