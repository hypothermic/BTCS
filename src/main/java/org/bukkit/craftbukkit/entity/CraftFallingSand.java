package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityFallingBlock;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingSand;

public class CraftFallingSand
  extends CraftEntity implements FallingSand
{
  public CraftFallingSand(CraftServer server, EntityFallingBlock entity)
  {
    super(server, entity);
  }
  
  public EntityFallingBlock getHandle()
  {
    return (EntityFallingBlock)this.entity;
  }
  
  public String toString()
  {
    return "CraftFallingSand";
  }
  
  public EntityType getType() {
    return EntityType.FALLING_BLOCK;
  }
}
