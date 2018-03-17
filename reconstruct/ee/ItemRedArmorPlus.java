package ee;

import forge.ArmorProperties;
import net.minecraft.server.DamageSource;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;

public class ItemRedArmorPlus extends ItemRedArmor
{
  public ItemRedArmorPlus(int var1, int var2, int var3)
  {
    super(var1, var2, var3);
    setMaxDurability(0);
  }
  
  public String getTextureFile()
  {
    return "/eqex/eqexsheet.png";
  }
  
  public void damageArmor(EntityLiving var1, ItemStack var2, DamageSource var3, int var4, int var5) {}
  
  public ArmorProperties getProperties(EntityLiving var1, ItemStack var2, DamageSource var3, double var4, int var6)
  {
    return (var6 != 0) && (var6 != 3) ? new ArmorProperties(0, 0.3D, 375) : (var6 == 0) && (var3 == DamageSource.FALL) ? new ArmorProperties(1, 1.0D, 15) : new ArmorProperties(0, 0.2D, 250);
  }
  
  public int getArmorDisplay(EntityHuman var1, ItemStack var2, int var3)
  {
    return (var3 != 0) && (var3 != 3) ? 6 : 4;
  }
}
