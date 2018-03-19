package forge;

import net.minecraft.server.ItemStack;

public abstract interface IFuelHandler
{
  public abstract int getItemBurnTime(ItemStack paramItemStack);
}
