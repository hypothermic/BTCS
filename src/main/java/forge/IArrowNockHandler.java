package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public abstract interface IArrowNockHandler
{
  public abstract ItemStack onArrowNock(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman);
}
