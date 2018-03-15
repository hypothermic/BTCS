package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityFlying;
import org.bukkit.craftbukkit.CraftServer;

public class CraftFlying extends CraftLivingEntity implements org.bukkit.entity.Flying
{
  public CraftFlying(CraftServer server, EntityFlying entity)
  {
    super(server, entity);
  }
  
  public EntityFlying getHandle()
  {
    return (EntityFlying)this.entity;
  }
  
  public String toString()
  {
    return "CraftFlying";
  }
}
