package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySkeleton;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;

public class CraftSkeleton extends CraftMonster implements Skeleton
{
  public CraftSkeleton(CraftServer server, EntitySkeleton entity)
  {
    super(server, entity);
  }
  
  public EntitySkeleton getHandle()
  {
    return (EntitySkeleton)this.entity;
  }
  
  public String toString()
  {
    return "CraftSkeleton";
  }
  
  public EntityType getType() {
    return EntityType.SKELETON;
  }
}
