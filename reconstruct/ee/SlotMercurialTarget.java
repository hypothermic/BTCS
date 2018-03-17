package ee;

import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Slot;






public class SlotMercurialTarget
  extends Slot
{
  private final int slotIndex;
  public int c;
  public int d;
  public int e;
  
  public SlotMercurialTarget(IInventory var1, int var2, int var3, int var4)
  {
    super(var1, var2, var3, var4);
    this.slotIndex = var2;
    this.d = var3;
    this.e = var4;
  }
  



  public boolean isAllowed(ItemStack var1)
  {
    return var1 == null;
  }
}
