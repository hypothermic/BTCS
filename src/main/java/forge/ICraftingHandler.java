package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

public abstract interface ICraftingHandler
{
  public abstract void onTakenFromCrafting(EntityHuman paramEntityHuman, ItemStack paramItemStack, IInventory paramIInventory);
}
