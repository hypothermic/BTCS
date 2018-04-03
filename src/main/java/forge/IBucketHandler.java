package forge;

import net.minecraft.server.ItemStack;
import net.minecraft.server.World;

public abstract interface IBucketHandler
{
  public abstract ItemStack fillCustomBucket(World paramWorld, int paramInt1, int paramInt2, int paramInt3);
}
