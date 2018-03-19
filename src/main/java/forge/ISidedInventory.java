package forge;

import net.minecraft.server.IInventory;

public abstract interface ISidedInventory
  extends IInventory
{
  public abstract int getStartInventorySide(int paramInt);
  
  public abstract int getSizeInventorySide(int paramInt);
}
