package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySnowman;
import org.bukkit.entity.EntityType;

public class CraftSnowman extends CraftGolem implements org.bukkit.entity.Snowman
{
  public CraftSnowman(org.bukkit.craftbukkit.CraftServer server, EntitySnowman entity)
  {
    super(server, entity);
  }
  
  public EntitySnowman getHandle()
  {
    return (EntitySnowman)this.entity;
  }
  
  public String toString()
  {
    return "CraftSnowman";
  }
  
  public EntityType getType() {
    return EntityType.SNOWMAN;
  }
}
