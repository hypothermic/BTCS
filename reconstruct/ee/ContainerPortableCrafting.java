package ee;

import net.minecraft.server.Container;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryCraftResult;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;
import net.minecraft.server.SlotResult;
import net.minecraft.server.World;

public class ContainerPortableCrafting extends Container
{
  public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
  public IInventory craftResult = new InventoryCraftResult();
  private World worldObj;
  private int posX;
  private int posY;
  private int posZ;
  
  public ContainerPortableCrafting(IInventory var1, EntityHuman var2)
  {
    this.worldObj = var2.world;
    this.craftMatrix.resultInventory = this.craftResult;
    setPlayer(var2);
    a(new SlotResult(var2, this.craftMatrix, this.craftResult, 0, 124, 35));
    

    int var3;
    int var4;
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 3; var4++)
      {
        a(new Slot(this.craftMatrix, var4 + var3 * 3, 30 + var4 * 18, 17 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 9; var4++)
      {
        a(new Slot(var2.inventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      a(new Slot(var2.inventory, var3, 8 + var3 * 18, 142));
    }
    
    a(this.craftMatrix);
  }
  

  public IInventory getInventory()
  {
    return this.craftMatrix;
  }
  


  public void a(IInventory var1)
  {
    this.craftResult.setItem(0, CraftingManager.getInstance().craft(this.craftMatrix));
  }
  



  public void a(EntityHuman var1)
  {
    super.a(var1);
    
    if (!this.worldObj.isStatic)
    {
      for (int var2 = 0; var2 < 9; var2++)
      {
        ItemStack var3 = this.craftMatrix.getItem(var2);
        
        if (var3 != null)
        {
          var1.drop(var3);
        }
      }
    }
  }
  
  public boolean b(EntityHuman var1)
  {
    return true;
  }
  



  public ItemStack a(int var1)
  {
    ItemStack var2 = null;
    Slot var3 = (Slot)this.e.get(var1);
    
    if ((var3 != null) && (var3.c()))
    {
      ItemStack var4 = var3.getItem();
      var2 = var4.cloneItemStack();
      
      if (var1 == 0)
      {
        if (!a(var4, 10, 46, true))
        {
          return null;
        }
      }
      else if ((var1 >= 1) && (var1 < 10))
      {
        if (!a(var4, 10, 46, false))
        {
          return null;
        }
      }
      else if ((var1 >= 10) && (var1 < 46))
      {
        if (!a(var4, 1, 10, false))
        {
          return null;
        }
      }
      else if (!a(var4, 10, 46, false))
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
      
      if (var4.count == var2.count)
      {
        return null;
      }
      
      var3.c(var4);
    }
    
    return var2;
  }
}
