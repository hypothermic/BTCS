package ee;

import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;






public class SlotTransmute
  extends Slot
{
  private final int slotIndex;
  public final TransTabletData transGrid;
  public int c;
  public int d;
  public int e;
  
  public SlotTransmute(TransTabletData var1, int var2, int var3, int var4)
  {
    super(var1, var2, var3, var4);
    this.transGrid = var1;
    this.slotIndex = var2;
    this.d = var3;
    this.e = var4;
  }
  



  public void c(ItemStack var1)
  {
    this.transGrid.latentEnergy += this.transGrid.currentEnergy - this.transGrid.kleinEMCTotal();
    this.transGrid.latentEnergy -= EEMaps.getEMC(var1);
    
    for (int var2 = 0; var2 < 8; var2++)
    {
      if (this.transGrid.items[var2] != null)
      {
        if ((this.transGrid.items[var2].getItem() instanceof ItemKleinStar))
        {
          if (this.transGrid.latentEnergy < 0)
          {
            if (((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]) > 0)
            {
              int var3 = -this.transGrid.latentEnergy;
              
              if (((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]) < var3)
              {
                var3 = ((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]);
              }
              
              this.transGrid.latentEnergy += var3;
              ((ItemKleinStar)this.transGrid.items[var2].getItem()).setKleinPoints(this.transGrid.items[var2], ((ItemKleinStar)this.transGrid.items[var2].getItem()).getKleinPoints(this.transGrid.items[var2]) - var3);
            }
            
            ((ItemKleinStar)this.transGrid.items[var2].getItem()).onUpdate(this.transGrid.items[var2]);
          }
        }
        else
        {
          this.transGrid.items[var2].count -= 1;
          
          if (this.transGrid.items[var2].count < 1)
          {
            this.transGrid.items[var2] = null;
          }
        }
      }
    }
    
    this.transGrid.calculateEMC();
    d();
  }
  



  public boolean isAllowed(ItemStack var1)
  {
    return false;
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
