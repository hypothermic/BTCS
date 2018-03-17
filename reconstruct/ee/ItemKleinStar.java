package ee;

import net.minecraft.server.EEProxy;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public class ItemKleinStar extends ItemEECharged
{
  public ItemKleinStar(int var1, int var2)
  {
    super(var1, 0);
    this.maxStackSize = 1;
    setMaxDurability(1001);
  }
  




  public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5)
  {
    if (!EEProxy.isClient(var2))
    {
      onUpdate(var1);
    }
  }
  
  public void onUpdate(ItemStack var1)
  {
    int var2 = EEBase.getKleinLevel(var1.id);
    
    if (getKleinPoints(var1) == 0)
    {
      var1.setData(0);
    }
    else if (var2 == 1)
    {
      if (getKleinPoints(var1) <= 50000)
      {
        var1.setData(var1.i() - getKleinPoints(var1) / 50);
      }
      else
      {
        var1.setData(1);
      }
    }
    else if (var2 == 2)
    {
      if (getKleinPoints(var1) <= 200000)
      {
        var1.setData(var1.i() - getKleinPoints(var1) / 200);
      }
      else
      {
        var1.setData(1);
      }
    }
    else if (var2 == 3)
    {
      if (getKleinPoints(var1) <= 800000)
      {
        var1.setData(var1.i() - getKleinPoints(var1) / 800);
      }
      else
      {
        var1.setData(1);
      }
    }
    else if (var2 == 4)
    {
      if (getKleinPoints(var1) <= 3200000)
      {
        var1.setData(var1.i() - getKleinPoints(var1) / 3200);
      }
      else
      {
        var1.setData(1);
      }
    }
    else if (var2 == 5)
    {
      if (getKleinPoints(var1) <= 12800000)
      {
        var1.setData(var1.i() - getKleinPoints(var1) / 12800);
      }
      else
      {
        var1.setData(1);
      }
    }
    else if (var2 == 6)
    {
      if (getKleinPoints(var1) <= 51200000)
      {
        var1.setData(var1.i() - getKleinPoints(var1) / 51200);
      }
      else
      {
        var1.setData(1);
      }
    }
  }
  
  public int getMaxPoints(ItemStack var1)
  {
    return EEBase.getKleinLevel(var1.id) == 6 ? 51200000 : EEBase.getKleinLevel(var1.id) == 5 ? 12800000 : EEBase.getKleinLevel(var1.id) == 4 ? 3200000 : EEBase.getKleinLevel(var1.id) == 3 ? 800000 : EEBase.getKleinLevel(var1.id) == 2 ? 200000 : EEBase.getKleinLevel(var1.id) == 1 ? 50000 : 0;
  }
  
  public int getKleinPoints(ItemStack var1)
  {
    return getInteger(var1, "points");
  }
  
  public void setKleinPoints(ItemStack var1, int var2)
  {
    setInteger(var1, "points", var2);
  }
  
  public void doChargeTick(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doUncharge(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doToggle(ItemStack var1, World var2, EntityHuman var3) {}
  
  public void doAlternate(ItemStack var1, World var2, EntityHuman var3)
  {
    var3.a("This Klein Star currently holds " + getKleinPoints(var1) + " EMC.");
  }
}
