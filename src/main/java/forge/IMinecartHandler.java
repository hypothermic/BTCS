package forge;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityMinecart;

public abstract interface IMinecartHandler
{
  public abstract void onMinecartUpdate(EntityMinecart paramEntityMinecart, int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void onMinecartEntityCollision(EntityMinecart paramEntityMinecart, Entity paramEntity);
  
  public abstract boolean onMinecartInteract(EntityMinecart paramEntityMinecart, EntityHuman paramEntityHuman, boolean paramBoolean);
}
