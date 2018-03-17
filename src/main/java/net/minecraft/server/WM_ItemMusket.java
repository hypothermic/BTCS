package net.minecraft.server;

import java.util.Random;

public class WM_ItemMusket extends WM_ItemReloadable
{
  public WM_ItemMusket(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon, 2);
    
    if (paramWM_EnumWeapon == WM_EnumWeapon.KNIFE)
    {
      setMaxDurability(getMaxDurability() * 2);
    }
  }
  
  protected void onReloadClick(int paramInt, ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman)
  {
    if (paramInt == this.reloadClicks)
    {
      paramEntityHuman.C_();
      paramWorld.makeSound(paramEntityHuman, "random.click", 1.0F, 1.0F / (Item.c.nextFloat() * 0.4F + 0.8F));
    }
    else if (paramInt == 1)
    {
      paramWorld.makeSound(paramEntityHuman, "note.hat", 2.0F, Item.c.nextFloat() * 0.4F + 0.4F);
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
    
    if (!paramWorld.isStatic)
    {
      WM_EntityMusketBullet localWM_EntityMusketBullet = new WM_EntityMusketBullet(paramWorld, paramEntityHuman, 1.0F / f);
      paramWorld.addEntity(localWM_EntityMusketBullet);
    }
    
    int j = 4;
    
    if (paramItemStack.getData() + j <= paramItemStack.i())
    {
      postShootingEffects(paramItemStack, paramEntityHuman, paramWorld, paramEntityHuman.isSneaking());
      resetReload(paramItemStack);
    }
    
    paramItemStack.damage(j, paramEntityHuman);
  }
  
  public int getAmmoItemId()
  {
    return mod_WeaponMod.musketBullet.id;
  }
  
  protected void postShootingEffects(ItemStack paramItemStack, EntityHuman paramEntityHuman, World paramWorld, boolean paramBoolean)
  {
    paramWorld.makeSound(paramEntityHuman, "random.explode", 3.0F, 1.0F / (Item.c.nextFloat() * 0.4F + 0.7F));
    paramWorld.makeSound(paramEntityHuman, "ambient.weather.thunder", 3.0F, 1.0F / (Item.c.nextFloat() * 0.4F + 0.4F));
    float f1 = paramBoolean ? -0.05F : -0.1F;
    double d1 = -MathHelper.sin(paramEntityHuman.yaw / 180.0F * 3.1415927F) * MathHelper.cos(0.0F) * f1;
    double d2 = MathHelper.cos(paramEntityHuman.yaw / 180.0F * 3.1415927F) * MathHelper.cos(0.0F) * f1;
    paramEntityHuman.pitch -= (paramBoolean ? 7.5F : 15.0F);
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
