package forge;

import net.minecraft.server.World;

public abstract interface IBonemealHandler
{
  public abstract boolean onUseBonemeal(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
