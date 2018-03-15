package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityThrownExpBottle;
import org.bukkit.entity.EntityType;

public class CraftThrownExpBottle extends CraftProjectile implements org.bukkit.entity.ThrownExpBottle
{
  public CraftThrownExpBottle(org.bukkit.craftbukkit.CraftServer server, EntityThrownExpBottle entity)
  {
    super(server, entity);
  }
  
  public EntityThrownExpBottle getHandle()
  {
    return (EntityThrownExpBottle)this.entity;
  }
  
  public String toString()
  {
    return "EntityThrownExpBottle";
  }
  
  public EntityType getType() {
    return EntityType.THROWN_EXP_BOTTLE;
  }
}
