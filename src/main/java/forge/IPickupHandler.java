package forge;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityItem;

public abstract interface IPickupHandler
{
  public abstract boolean onItemPickup(EntityHuman paramEntityHuman, EntityItem paramEntityItem);
}
