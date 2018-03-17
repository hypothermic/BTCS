package ee;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.Slot;

public class ContainerPedestal extends Container
{
  private TilePedestal entity;
  
  public ContainerPedestal(IInventory var1, TilePedestal var2)
  {
    this.entity = var2;
    setPlayer(((PlayerInventory)var1).player);
    a(new Slot(var2, 0, 80, 20));
    
    int var3;
    int var4;
    for (var3 = 0; var3 < 3; var3++)
    {
      for (var4 = 0; var4 < 9; var4++)
      {
        a(new Slot(var1, var4 + var3 * 9 + 9, 8 + var4 * 18, 54 + var3 * 18));
      }
    }
    
    for (var3 = 0; var3 < 9; var3++)
    {
      a(new Slot(var1, var3, 8 + var3 * 18, 112));
    }
  }
  
  public IInventory getInventory()
  {
    return this.entity;
  }
  
  public boolean b(EntityHuman var1)
  {
    return this.entity.a(var1);
  }
  



  public ItemStack a(int var1)
  {
    return null;
  }
}
