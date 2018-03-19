package forge;

import net.minecraft.server.World;

public abstract interface IOverrideReplace
{
  public abstract boolean canReplaceBlock(World paramWorld, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract boolean getReplacedSuccess();
}
