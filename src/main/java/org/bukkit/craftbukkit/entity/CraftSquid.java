package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySquid;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Squid;

public class CraftSquid extends CraftWaterMob implements Squid
{
  public CraftSquid(CraftServer server, EntitySquid entity)
  {
    super(server, entity);
  }
  
  public EntitySquid getHandle()
  {
    return (EntitySquid)this.entity;
  }
  
  public String toString()
  {
    return "CraftSquid";
  }
  
  public EntityType getType() {
    return EntityType.SQUID;
  }
}
