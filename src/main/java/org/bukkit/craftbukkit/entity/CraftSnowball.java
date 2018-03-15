package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySnowball;
import org.bukkit.entity.EntityType;

public class CraftSnowball extends CraftProjectile implements org.bukkit.entity.Snowball
{
  public CraftSnowball(org.bukkit.craftbukkit.CraftServer server, EntitySnowball entity)
  {
    super(server, entity);
  }
  
  public EntitySnowball getHandle()
  {
    return (EntitySnowball)this.entity;
  }
  
  public String toString()
  {
    return "CraftSnowball";
  }
  
  public EntityType getType() {
    return EntityType.SNOWBALL;
  }
}
