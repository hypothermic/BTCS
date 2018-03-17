package ee;

import net.minecraft.server.EEProxy;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemHyperkineticLens extends ItemEECharged
{
  public boolean itemCharging;
  
  public ItemHyperkineticLens(int var1)
  {
    super(var1, 3);
  }
  




  public boolean interactWith(ItemStack var1, EntityHuman var2, World var3, int var4, int var5, int var6, int var7)
  {
    return false;
  }
  
  public void doBreak(ItemStack var1, World var2, EntityHuman var3)
  {
    int var4 = 1;
    
    if (chargeLevel(var1) > 0)
    {
      var4++;
    }
    
    if (chargeLevel(var1) > 1)
    {
      var4++;
      var4++;
    }
    
    if (chargeLevel(var1) > 2)
    {
      var4++;
      var4++;
    }
    
    var2.makeSound(var3, "wall", 1.0F, 1.0F);
    var2.addEntity(new EntityHyperkinesis(var2, var3, chargeLevel(var1), var4));
  }
  



  public ItemStack a(ItemStack var1, World var2, EntityHuman var3)
  {
    if (EEProxy.isClient(var2))
    {
      return var1;
    }
    

    doBreak(var1, var2, var3);
    return var1;
  }
  

  public void doRelease(ItemStack var1, World var2, EntityHuman var3)
  {
    if (!EEProxy.isClient(var2))
    {
      doBreak(var1, var2, var3);
    }
  }
  
  public void doLeftClick(ItemStack var1, World var2, EntityHuman var3)
  {
    if (!EEProxy.isClient(var2))
    {
      doBreak(var1, var2, var3);
    }
  }
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
}
