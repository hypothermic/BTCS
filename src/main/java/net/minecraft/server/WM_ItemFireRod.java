package net.minecraft.server;

import java.util.Random;

public class WM_ItemFireRod extends WM_ItemWeaponMod
{
  public WM_ItemFireRod(int paramInt, EnumToolMaterial paramEnumToolMaterial, WM_EnumWeapon paramWM_EnumWeapon)
  {
    super(paramInt, paramEnumToolMaterial, paramWM_EnumWeapon);
  }
  




  public boolean a(ItemStack paramItemStack, EntityLiving paramEntityLiving1, EntityLiving paramEntityLiving2)
  {
    super.a(paramItemStack, paramEntityLiving1, paramEntityLiving2);
    paramEntityLiving1.setOnFire(12 + c.nextInt(3));
    return true;
  }
  




  public void a(ItemStack paramItemStack, World paramWorld, Entity paramEntity, int paramInt, boolean paramBoolean)
  {
    if (((paramEntity instanceof EntityHuman)) && (((EntityHuman)paramEntity).inventory.getItemInHand() == paramItemStack) && (!paramEntity.a(Material.WATER)))
    {
      float f1 = 1.0F;
      float f2 = 27.0F;
      float f3 = -MathHelper.sin((paramEntity.yaw + f2) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntity.pitch / 180.0F * 3.1415927F) * f1;
      float f4 = -MathHelper.sin(paramEntity.pitch / 180.0F * 3.1415927F) - 0.1F;
      float f5 = MathHelper.cos((paramEntity.yaw + f2) / 180.0F * 3.1415927F) * MathHelper.cos(paramEntity.pitch / 180.0F * 3.1415927F) * f1;
      
      if (c.nextInt(5) == 0)
      {
        paramWorld.a("flame", paramEntity.locX + f3, paramEntity.locY + f4, paramEntity.locZ + f5, 0.0D, 0.0D, 0.0D);
      }
      
      if (c.nextInt(5) == 0)
      {
        paramWorld.a("smoke", paramEntity.locX + f3, paramEntity.locY + f4, paramEntity.locZ + f5, 0.0D, 0.0D, 0.0D);
      }
    }
  }
}
