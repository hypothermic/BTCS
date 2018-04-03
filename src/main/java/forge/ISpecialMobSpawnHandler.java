package forge;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.World;

@Deprecated
public abstract interface ISpecialMobSpawnHandler
{
  public abstract boolean onSpecialEntitySpawn(EntityLiving paramEntityLiving, World paramWorld, float paramFloat1, float paramFloat2, float paramFloat3);
}
