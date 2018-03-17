package ee;

import java.util.List;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;

public class ContainerAlchChest extends net.minecraft.server.Container
{
  private boolean freeRoaming;
  private IInventory lowerChestInventory;
  private int numRows;
  
  public ContainerAlchChest(IInventory var1, IInventory var2, boolean var3)
  {
    this.freeRoaming = (!var3);
    this.lowerChestInventory = var1;
    setPlayer(((PlayerInventory)var2).player);
    this.lowerChestInventory.f();
    this.numRows = (var1.getSize() / 13);
    
    int var4;
    for (var4 = 0; var4 < this.numRows; var4++)
    {
      for (int var5 = 0; var5 < 13; var5++)
      {
        a(new Slot(var1, var5 + var4 * 13, 12 + var5 * 18, 5 + var4 * 18));
      }
    }
    
    for (var4 = 0; var4 < 3; var4++)
    {
      for (int var5 = 0; var5 < 9; var5++)
      {
        a(new Slot(var2, var5 + var4 * 9 + 9, 48 + var5 * 18, 152 + var4 * 18));
      }
    }
    
    for (var4 = 0; var4 < 9; var4++)
    {
      a(new Slot(var2, var4, 48 + var4 * 18, 210));
    }
  }
  
  public IInventory getInventory()
  {
    return this.lowerChestInventory;
  }
  
  public boolean b(EntityHuman var1)
  {
    return this.freeRoaming ? true : this.lowerChestInventory.a(var1);
  }
  



  public ItemStack a(int var1)
  {
    ItemStack var2 = null;
    Slot var3 = (Slot)this.e.get(var1);
    
    if ((var3 != null) && (var3.c()))
    {
      ItemStack var4 = var3.getItem();
      var2 = var4.cloneItemStack();
      
      if (var1 < this.numRows * 13)
      {
        if (!a(var4, this.numRows * 13, this.e.size(), true))
        {
          return null;
        }
      }
      else if (!a(var4, 0, this.numRows * 13, false))
      {
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
    }
    
    return var2;
  }
  



  public void a(EntityHuman var1)
  {
    super.a(var1);
    this.lowerChestInventory.g();
  }
}
