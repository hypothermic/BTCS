package forge;

import net.minecraft.server.ItemStack;

public abstract interface IOreHandler
{
  public abstract void registerOre(String paramString, ItemStack paramItemStack);
}
