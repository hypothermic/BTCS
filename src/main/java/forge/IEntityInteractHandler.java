package forge;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;

public abstract interface IEntityInteractHandler
{
  public abstract boolean onEntityInteract(EntityHuman paramEntityHuman, Entity paramEntity, boolean paramBoolean);
}
