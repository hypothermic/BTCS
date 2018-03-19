package forge;

import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;

public abstract interface ISpecialArmor
{
  public abstract ArmorProperties getProperties(EntityLiving paramEntityLiving, ItemStack paramItemStack, DamageSource paramDamageSource, double paramDouble, int paramInt);
  
  public abstract int getArmorDisplay(EntityHuman paramEntityHuman, ItemStack paramItemStack, int paramInt);
  
  public abstract void damageArmor(EntityLiving paramEntityLiving, ItemStack paramItemStack, DamageSource paramDamageSource, int paramInt1, int paramInt2);
}
