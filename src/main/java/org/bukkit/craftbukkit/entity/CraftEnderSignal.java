package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityEnderSignal;
import org.bukkit.entity.EntityType;

public class CraftEnderSignal extends CraftEntity implements org.bukkit.entity.EnderSignal
{
  public CraftEnderSignal(org.bukkit.craftbukkit.CraftServer server, EntityEnderSignal entity)
  {
    super(server, entity);
  }
  
  public EntityEnderSignal getHandle()
  {
    return (EntityEnderSignal)this.entity;
  }
  
  public String toString()
  {
    return "CraftEnderSignal";
  }
  
  public EntityType getType() {
    return EntityType.ENDER_SIGNAL;
  }
}
