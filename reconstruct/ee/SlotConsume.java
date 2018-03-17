package ee;

import net.minecraft.server.EEProxy;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;






public class SlotConsume
  extends Slot
{
  private final int slotIndex;
  public final TransTabletData transGrid;
  public int c;
  public int d;
  public int e;
  
  public SlotConsume(TransTabletData var1, int var2, int var3, int var4)
  {
    super(var1, var2, var3, var4);
    this.transGrid = var1;
    this.slotIndex = var2;
    this.d = var3;
    this.e = var4;
  }
  



  public void c(ItemStack var1)
  {
    d();
  }
  



  public boolean isAllowed(ItemStack var1)
  {
    return var1 == null;
  }
  



  public ItemStack getItem()
  {
    return this.transGrid.getItem(this.slotIndex);
  }
  



  public boolean c()
  {
    return getItem() != null;
  }
  



  public void set(ItemStack var1)
  {
    d();
    
    if (!EEProxy.isClient(EEProxy.theWorld))
    {
      if (var1 != null)
      {
        if (!EEBase.isKleinStar(var1.id))
        {
          if ((EEMaps.getEMC(var1) != 0) || (var1.getItem() == EEItem.alchemyTome))
          {
            if (this.transGrid.matchesLock(var1))
            {
              if ((!this.transGrid.isFuelLocked()) && (!this.transGrid.isMatterLocked()))
              {
                if (EEMaps.isFuel(var1))
                {
                  this.transGrid.fuelLock();
                }
                else
                {
                  this.transGrid.matterLock();
                }
              }
              
              if (!this.transGrid.playerKnows(var1.id, var1.getData()))
              {
                if (var1.getItem() == EEItem.alchemyTome)
                {
                  this.transGrid.pushTome();
                }
                
                this.transGrid.pushKnowledge(var1.id, var1.getData());
                this.transGrid.learned = 60;
              }
              
              while (var1.count > 0)
              {
                var1.count -= 1;
                this.transGrid.latentEnergy += EEMaps.getEMC(var1);
              }
            }
            
            if (var1.count == 0)
            {
              set(null);
            }
            
            d();
          }
        }
      }
    }
  }
  



  public void d()
  {
    this.transGrid.update();
  }
  




  public int a()
  {
    return this.transGrid.getMaxStackSize();
  }
  
  public int getBackgroundIconIndex()
  {
    return -1;
  }
  




  public ItemStack a(int var1)
  {
    return this.transGrid.splitStack(this.slotIndex, var1);
  }
}
