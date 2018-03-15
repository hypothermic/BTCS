package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityCaveSpider;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftCaveSpider extends CraftSpider implements org.bukkit.entity.CaveSpider
{
  public CraftCaveSpider(CraftServer server, EntityCaveSpider entity)
  {
    super(server, entity);
  }
  
  public EntityCaveSpider getHandle()
  {
    return (EntityCaveSpider)this.entity;
  }
  
  public String toString()
  {
    return "CraftCaveSpider";
  }
  
  public EntityType getType() {
    return EntityType.CAVE_SPIDER;
  }
}
