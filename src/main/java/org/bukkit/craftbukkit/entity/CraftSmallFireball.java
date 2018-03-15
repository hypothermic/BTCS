package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySmallFireball;
import org.bukkit.entity.EntityType;

public class CraftSmallFireball extends CraftFireball implements org.bukkit.entity.SmallFireball
{
  public CraftSmallFireball(org.bukkit.craftbukkit.CraftServer server, EntitySmallFireball entity)
  {
    super(server, entity);
  }
  
  public EntitySmallFireball getHandle()
  {
    return (EntitySmallFireball)this.entity;
  }
  
  public String toString()
  {
    return "CraftSmallFireball";
  }
  
  public EntityType getType() {
    return EntityType.SMALL_FIREBALL;
  }
}
