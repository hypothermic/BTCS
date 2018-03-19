package forge;

import net.minecraft.server.IBlockAccess;

public abstract interface IConnectRedstone
{
  public abstract boolean canConnectRedstone(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
