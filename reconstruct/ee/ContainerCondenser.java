package ee;

import java.util.List;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;

public class ContainerCondenser extends Container
{
  public long currentItemProgress = 0L;
  public int energy = 0;
  public long itemValue = 0L;
  public TileCondenser eCon;
  
  public ContainerCondenser(PlayerInventory var1, TileCondenser var2)
  {
    this.eCon = var2;
    setPlayer(var1.player);
    this.eCon.f();
    a(new Slot(var2, 0, 12, 6));
    
    int var3;
    int var4;
    for (var3 = 0; var3 <= 6; var3++)
    {
      for (var4 = 0; var4 <= 12; var4++)
      {
        a(new Slot(var2, 1 + var4 + var3 * 13, 12 + var4 * 18, 26 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 9; var4++)
      {
        a(new Slot(var1, var4 + var3 * 9 + 9, 48 + var4 * 18, 154 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      a(new Slot(var1, var3, 48 + var3 * 18, 212));
    }
  }
  
  public net.minecraft.server.IInventory getInventory()
  {
    return this.eCon;
  }
  


  public void a()
  {
    super.a();
    
    for (int var1 = 0; var1 < this.listeners.size(); var1++)
    {
      ICrafting var2 = (ICrafting)this.listeners.get(var1);
      
      if (this.currentItemProgress != this.eCon.currentItemProgress)
      {
        var2.setContainerData(this, 0, this.eCon.currentItemProgress);
      }
      
      if (this.energy != this.eCon.scaledEnergy)
      {
        var2.setContainerData(this, 1, this.eCon.scaledEnergy & 0xFFFF);
      }
      
      if (this.energy != this.eCon.scaledEnergy)
      {
        var2.setContainerData(this, 2, this.eCon.scaledEnergy >>> 16);
      }
    }
    
    this.currentItemProgress = this.eCon.currentItemProgress;
    this.energy = this.eCon.scaledEnergy;
  }
  
  public boolean b(EntityHuman var1)
  {
    return this.eCon.a(var1);
  }
  



  public ItemStack a(int var1)
  {
    ItemStack var2 = null;
    Slot var3 = (Slot)this.e.get(var1);
    
    if ((var3 != null) && (var3.c()))
    {
      ItemStack var4 = var3.getItem();
      var2 = var4.cloneItemStack();
      
      if ((var1 >= 1) && (var1 <= 91))
      {
        if (!a(var4, 92, 127, false))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 92) && (var1 <= 127) && (!a(var4, 1, 91, false)))
      {
        if (var4.count == 0)
        {
          var3.set(null);
        }
        
        return null;
      }
      
      if (var4.count == 0)
      {
        var3.set(null);
      }
      else
      {
        var3.d();
      }
      
      if (var4.count == var2.count)
      {
        return null;
      }
      
      var3.c(var4);
    }
    
    return var2;
  }
  



  public void a(EntityHuman var1)
  {
    super.a(var1);
    this.eCon.g();
  }
}
