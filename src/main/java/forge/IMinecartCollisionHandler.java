package forge;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Entity;
import net.minecraft.server.EntityMinecart;

public abstract interface IMinecartCollisionHandler
{
  public abstract void onEntityCollision(EntityMinecart paramEntityMinecart, Entity paramEntity);
  
  public abstract AxisAlignedBB getCollisionBox(EntityMinecart paramEntityMinecart, Entity paramEntity);
  
  public abstract AxisAlignedBB getMinecartCollisionBox(EntityMinecart paramEntityMinecart);
  
  public abstract AxisAlignedBB getBoundingBox(EntityMinecart paramEntityMinecart);
}
