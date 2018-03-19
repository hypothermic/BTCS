package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;

public abstract interface IDestroyToolHandler
{
  public abstract void onDestroyCurrentItem(EntityHuman paramEntityHuman, ItemStack paramItemStack);
}
