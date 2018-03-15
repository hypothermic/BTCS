package forge;

import net.minecraft.server.Entity;
import net.minecraft.server.World;

public abstract interface ISpecialResistance
{
  public abstract float getSpecialExplosionResistance(World paramWorld, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3, Entity paramEntity);
}
