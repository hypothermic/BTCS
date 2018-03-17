package ee;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public abstract class ItemDarkTool extends ItemEECharged
{
  private Block[] blocksEffectiveAgainst;
  private float efficiencyOnProperMaterial = 14.0F;
  
  protected ItemDarkTool(int var1, int var2, int var3, Block[] var4)
  {
    super(var1, var2);
    this.blocksEffectiveAgainst = var4;
    this.weaponDamage = (var3 + 3);
  }
  




  public float getDestroySpeed(ItemStack var1, Block var2)
  {
    for (int var3 = 0; var3 < this.blocksEffectiveAgainst.length; var3++)
    {
      if (this.blocksEffectiveAgainst[var3] == var2)
      {
        return this.efficiencyOnProperMaterial;
      }
    }
    
    return 1.0F;
  }
  



  public int a(Entity var1)
  {
    return this.weaponDamage;
  }
  




  public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
  {
    return true;
  }
  
  public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
  {
    return true;
  }
  
  public boolean isFull3D()
  {
    return true;
  }
  
  public void doBreak(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doPassive(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doActive(ItemStack var1, World var2, EntityHuman var3) {}
}
