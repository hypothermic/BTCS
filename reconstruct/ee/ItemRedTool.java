package ee;

import net.minecraft.server.Block;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public abstract class ItemRedTool extends ItemEECharged
{
  private Block[] blocksEffectiveAgainst;
  private float efficiencyOnProperMaterial = 18.0F;
  private int damageVsEntity;
  
  protected ItemRedTool(int var1, int var2, int var3, Block[] var4)
  {
    super(var1, var2);
    this.blocksEffectiveAgainst = var4;
    this.weaponDamage = (var3 + 5);
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
    
    return 2.0F;
  }
  
  public boolean a(ItemStack var1, int var2, int var3, int var4, int var5, EntityLiving var6)
  {
    return true;
  }
  



  public int a(Entity var1)
  {
    return this.weaponDamage;
  }
  




  public boolean a(ItemStack var1, EntityLiving var2, EntityLiving var3)
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
