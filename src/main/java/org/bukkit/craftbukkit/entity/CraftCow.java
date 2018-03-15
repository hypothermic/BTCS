package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityCow;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;

public class CraftCow extends CraftAnimals implements Cow
{
  public CraftCow(CraftServer server, EntityCow entity)
  {
    super(server, entity);
  }
  
  public EntityCow getHandle()
  {
    return (EntityCow)this.entity;
  }
  
  public String toString()
  {
    return "CraftCow";
  }
  
  public EntityType getType() {
    return EntityType.COW;
  }
}
