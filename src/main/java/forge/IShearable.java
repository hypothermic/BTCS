package forge;

import java.util.ArrayList;
import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public abstract interface IShearable
{
  public abstract boolean isShearable(ItemStack paramItemStack, World paramWorld, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract ArrayList<ItemStack> onSheared(ItemStack paramItemStack, World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
