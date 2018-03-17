package ee;

import java.util.List;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;

public class ContainerCollector3 extends Container
{
  private TileCollector3 collector;
  private int sunStatus = 0;
  private int sunTimeScaled = 0;
  private int currentFuelProgress = 0;
  private boolean isUsingPower;
  private int kleinProgressScaled = 0;
  private int kleinPoints = 0;
  private int sunTime = 0;
  
  public ContainerCollector3(PlayerInventory var1, TileCollector3 var2)
  {
    this.collector = var2;
    setPlayer(var1.player);
    a(new Slot(var2, 0, 158, 58));
    


    int var3;
    int var4;
    for (var3 = 0; var3 <= 3; var3++)
    {
      for (var4 = 0; var4 <= 3; var4++)
      {
        a(new Slot(var2, var3 * 4 + var4 + 1, 18 + var3 * 18, 8 + var4 * 18));
      }
    }
    
    a(new Slot(var2, 17, 158, 13));
    a(new Slot(var2, 18, 187, 36));
    
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 9; var4++)
      {
        a(new Slot(var1, var4 + var3 * 9 + 9, 30 + var4 * 18, 84 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      a(new Slot(var1, var3, 30 + var3 * 18, 142));
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
      
      if (var1 <= 18)
      {
        if (!a(var4, 19, 54, true))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 19) && (var1 < 46))
      {
        if (EEMaps.isFuel(var4))
        {
          if (!a(var4, 0, 16, true))
          {
            if (var4.count == 0)
            {
              var3.set(null);
            }
            
            return null;
          }
        }
        else if (!a(var4, 46, 54, false))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 46) && (var1 <= 54))
      {
        if ((EEMaps.isFuel(var4)) && (!a(var4, 0, 16, true)))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if (!a(var4, 19, 54, false))
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
