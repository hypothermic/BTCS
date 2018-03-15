package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public abstract interface IArrowLooseHandler
{
  public abstract boolean onArrowLoose(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman, int paramInt);
}
