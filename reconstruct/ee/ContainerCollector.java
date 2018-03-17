package ee;

import java.util.List;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;

public class ContainerCollector extends Container
{
  private TileCollector collector;
  private int sunStatus = 0;
  private int sunTimeScaled = 0;
  private int currentFuelProgress = 0;
  private boolean isUsingPower;
  private int kleinProgressScaled = 0;
  private int kleinPoints = 0;
  private int sunTime = 0;
  
  public ContainerCollector(PlayerInventory var1, TileCollector var2)
  {
    setPlayer(var1.player);
    this.collector = var2;
    a(new Slot(var2, 0, 124, 58));

    int var3;
    int var4;
    for (var3 = 0; var3 <= 1; var3++)
    {
      for (var4 = 0; var4 <= 3; var4++)
      {
        a(new Slot(var2, var4 * 2 + var3 + 1, 20 + var3 * 18, 8 + var4 * 18));
      }
    }
    
    a(new Slot(var2, 9, 124, 13));
    a(new Slot(var2, 10, 153, 36));
    
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 9; var4++)
      {
        a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      a(new Slot(var1, var3, 8 + var3 * 18, 142));
    }
  }
  
  public net.minecraft.server.IInventory getInventory()
  {
    return this.collector;
  }
  
  public boolean b(EntityHuman var1)
  {
    return this.collector.a(var1);
  }
  



  public void a()
  {
    super.a();
    
    for (int var1 = 0; var1 < this.listeners.size(); var1++)
    {
      ICrafting var2 = (ICrafting)this.listeners.get(var1);
      
      if (this.sunTimeScaled != this.collector.sunTimeScaled)
      {
        var2.setContainerData(this, 0, this.collector.sunTimeScaled);
      }
      
      if (this.sunStatus != this.collector.currentSunStatus)
      {
        var2.setContainerData(this, 1, this.collector.currentSunStatus);
      }
      
      if (this.currentFuelProgress != this.collector.currentFuelProgress)
      {
        var2.setContainerData(this, 2, this.collector.currentFuelProgress);
      }
      
      if (this.isUsingPower != this.collector.isUsingPower)
      {
        var2.setContainerData(this, 3, this.collector.isUsingPower ? 1 : 0);
      }
      
      if (this.isUsingPower != this.collector.isUsingPower)
      {
        var2.setContainerData(this, 4, this.collector.isUsingPower ? 1 : 0);
      }
      
      if (this.kleinProgressScaled != this.collector.kleinProgressScaled)
      {
        var2.setContainerData(this, 5, this.collector.kleinProgressScaled);
      }
      
      if (this.kleinPoints != this.collector.kleinPoints)
      {
        var2.setContainerData(this, 6, this.collector.kleinPoints & 0xFFFF);
      }
      
      if (this.kleinPoints != this.collector.kleinPoints)
      {
        var2.setContainerData(this, 7, this.collector.kleinPoints >>> 16);
      }
      
      if (this.sunTime != this.collector.collectorSunTime)
      {
        var2.setContainerData(this, 8, this.collector.collectorSunTime & 0xFFFF);
      }
      
      if (this.sunTime != this.collector.collectorSunTime)
      {
        var2.setContainerData(this, 9, this.collector.collectorSunTime >>> 16);
      }
    }
    
    this.sunTime = this.collector.collectorSunTime;
    this.kleinPoints = this.collector.kleinPoints;
    this.isUsingPower = this.collector.isUsingPower;
    this.sunTimeScaled = this.collector.sunTimeScaled;
    this.sunStatus = this.collector.currentSunStatus;
    this.currentFuelProgress = this.collector.currentFuelProgress;
    this.kleinProgressScaled = this.collector.kleinProgressScaled;
  }
  



  public ItemStack a(int var1)
  {
    ItemStack var2 = null;
    Slot var3 = (Slot)this.e.get(var1);
    
    if ((var3 != null) && (var3.c()))
    {
      ItemStack var4 = var3.getItem();
      var2 = var4.cloneItemStack();
      
      if (var1 <= 10)
      {
        if (!a(var4, 11, 46, true))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 11) && (var1 < 38))
      {
        if (EEMaps.isFuel(var4))
        {
          if (!a(var4, 0, 8, true))
          {
            if (var4.count == 0)
            {
              var3.set(null);
            }
            
            return null;
          }
        }
        else if (!a(var4, 38, 46, false))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 38) && (var1 <= 46))
      {
        if ((EEMaps.isFuel(var4)) && (!a(var4, 0, 8, true)))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if (!a(var4, 11, 46, false))
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
}
