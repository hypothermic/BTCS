package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityGhast;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ghast;

public class CraftGhast extends CraftFlying implements Ghast
{
  public CraftGhast(CraftServer server, EntityGhast entity)
  {
    super(server, entity);
  }
  
  public EntityGhast getHandle()
  {
    return (EntityGhast)this.entity;
  }
  
  public String toString()
  {
    return "CraftGhast";
  }
  
  public EntityType getType() {
    return EntityType.GHAST;
  }
}
