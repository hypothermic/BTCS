package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityBlaze;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftBlaze extends CraftMonster implements org.bukkit.entity.Blaze
{
  public CraftBlaze(CraftServer server, EntityBlaze entity)
  {
    super(server, entity);
  }
  
  public EntityBlaze getHandle()
  {
    return (EntityBlaze)this.entity;
  }
  
  public String toString()
  {
    return "CraftBlaze";
  }
  
  public EntityType getType() {
    return EntityType.BLAZE;
  }
}
