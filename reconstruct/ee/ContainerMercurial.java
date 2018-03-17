package ee;

import net.minecraft.server.Container;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;
import net.minecraft.server.World;

public class ContainerMercurial extends Container
{
  public EntityHuman player;
  public MercurialEyeData eye;
  private World worldObj;
  
  public ContainerMercurial(IInventory var1, EntityHuman var2, MercurialEyeData var3)
  {
    this.player = var2;
    setPlayer(var2);
    this.eye = var3;
    a(new Slot(this.eye, 0, 51, 26));
    a(new SlotMercurialTarget(this.eye, 1, 105, 26));
    this.worldObj = this.player.world;
    
    int var4;
    int var5;
    for (var4 = 0; var4 < 3; var4++)
    {
      for (var5 = 0; var5 < 9; var5++)
      {
        a(new Slot(this.player.inventory, var5 + var4 * 9 + 9, 6 + var5 * 18, 55 + var4 * 18));
      }
    }
    
    for (var4 = 0; var4 < 9; var4++)
    {
      a(new Slot(this.player.inventory, var4, 6 + var4 * 18, 113));
    }
  }
  
  public IInventory getInventory()
  {
    return this.eye;
  }
  
  public int getKleinStarPoints(ItemStack var1)
  {
    return (var1.getItem() instanceof ItemKleinStar) ? ((ItemKleinStar)var1.getItem()).getKleinPoints(var1) : var1 == null ? 0 : 0;
  }
  
  public boolean b(EntityHuman var1)
  {
    return true;
  }

  public ItemStack a(int var1)
  {
    return null;
  }
}
