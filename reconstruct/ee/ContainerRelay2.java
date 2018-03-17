package ee;

import java.util.List;
import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.ICrafting;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;

public class ContainerRelay2 extends Container
{
  private int kleinDrain = 0;
  private int kleinCharge = 0;
  private int relayEnergy = 0;
  private int kleinDrainPoints = 0;
  private int kleinChargePoints = 0;
  private int relayEnergyPoints = 0;
  private TileRelay2 array;
  private int cookTime = 0;
  private int burnTime = 0;
  private int itemBurnTime;
  
  public ContainerRelay2(PlayerInventory var1, TileRelay2 var2)
  {
    this.array = var2;
    setPlayer(var1.player);
    a(new Slot(var2, 0, 84, 44));
    
    int var3;
    int var4;
    for (var3 = 0; var3 <= 2; var3++)
    {
      for (var4 = 0; var4 <= 3; var4++)
      {
        a(new Slot(var2, var3 * 4 + var4 + 1, 26 + var3 * 18, 18 + var4 * 18));
      }
    }
    
    a(new Slot(var2, 13, 144, 44));
    
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 9; var4++)
      {
        a(new Slot(var1, var4 + var3 * 9 + 9, 16 + var4 * 18, 101 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      a(new Slot(var1, var3, 16 + var3 * 18, 159));
    }
  }
  
  public net.minecraft.server.IInventory getInventory()
  {
    return this.array;
  }

  public void a()
  {
    super.a();
    
    for (int var1 = 0; var1 < this.listeners.size(); var1++)
    {
      ICrafting var2 = (ICrafting)this.listeners.get(var1);
      
      if (this.cookTime != this.array.cookProgressScaled)
      {
        var2.setContainerData(this, 0, this.array.cookProgressScaled);
      }
      
      if (this.burnTime != this.array.burnTimeRemainingScaled)
      {
        var2.setContainerData(this, 1, this.array.burnTimeRemainingScaled);
      }
      
      if (this.kleinDrain != this.array.kleinDrainingScaled)
      {
        var2.setContainerData(this, 2, this.array.kleinDrainingScaled);
      }
      
      if (this.kleinCharge != this.array.kleinChargingScaled)
      {
        var2.setContainerData(this, 3, this.array.kleinChargingScaled);
      }
      
      if (this.relayEnergy != this.array.relayEnergyScaled)
      {
        var2.setContainerData(this, 4, this.array.relayEnergyScaled);
      }
      
      if (this.kleinChargePoints != this.array.kleinChargePoints)
      {
        var2.setContainerData(this, 5, this.array.kleinChargePoints & 0xFFFF);
      }
      
      if (this.kleinChargePoints != this.array.kleinChargePoints)
      {
        var2.setContainerData(this, 6, this.array.kleinChargePoints >>> 16);
      }
      
      if (this.kleinDrainPoints != this.array.kleinDrainPoints)
      {
        var2.setContainerData(this, 7, this.array.kleinDrainPoints & 0xFFFF);
      }
      
      if (this.kleinDrainPoints != this.array.kleinDrainPoints)
      {
        var2.setContainerData(this, 8, this.array.kleinDrainPoints >>> 16);
      }
      
      if (this.relayEnergyPoints != this.array.scaledEnergy)
      {
        var2.setContainerData(this, 9, this.array.scaledEnergy & 0xFFFF);
      }
      
      if (this.relayEnergyPoints != this.array.scaledEnergy)
      {
        var2.setContainerData(this, 10, this.array.scaledEnergy >>> 16);
      }
    }
    
    this.cookTime = this.array.cookProgressScaled;
    this.burnTime = this.array.burnTimeRemainingScaled;
    this.kleinDrain = this.array.kleinDrainingScaled;
    this.kleinCharge = this.array.kleinChargingScaled;
    this.relayEnergy = this.array.relayEnergyScaled;
    this.kleinChargePoints = this.array.kleinChargePoints;
    this.kleinDrainPoints = this.array.kleinDrainPoints;
    this.relayEnergyPoints = this.array.scaledEnergy;
  }
  
  public void updateProgressBar(int var1, int var2)
  {
    if (var1 == 0)
    {
      this.array.arrayCounter = var2;
    }
  }
  
  public boolean b(EntityHuman var1)
  {
    return this.array.a(var1);
  }
  



  public ItemStack a(int var1)
  {
    ItemStack var2 = null;
    Slot var3 = (Slot)this.e.get(var1);
    
    if ((var3 != null) && (var3.c()))
    {
      ItemStack var4 = var3.getItem();
      var2 = var4.cloneItemStack();
      
      if (var1 == 13)
      {
        if (!a(var4, 14, 47, true))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 14) && (var1 <= 40))
      {
        if (EEMaps.getEMC(var4.id) > 0)
        {
          if (!a(var4, 0, 13, true))
          {
            if (var4.count == 0)
            {
              var3.set(null);
            }
            
            return null;
          }
        }
        else if (!a(var4, 41, 49, false))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if ((var1 >= 41) && (var1 <= 49))
      {
        if (EEMaps.getEMC(var4.id) > 0)
        {
          if (!a(var4, 0, 13, true))
          {
            if (var4.count == 0)
            {
              var3.set(null);
            }
            
            return null;
          }
        }
        else if (!a(var4, 14, 40, false))
        {
          if (var4.count == 0)
          {
            var3.set(null);
          }
          
          return null;
        }
      }
      else if (!a(var4, 14, 49, false))
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
