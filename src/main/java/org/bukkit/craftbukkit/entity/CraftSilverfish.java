package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySilverfish;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftSilverfish extends CraftMonster implements org.bukkit.entity.Silverfish
{
  public CraftSilverfish(CraftServer server, EntitySilverfish entity)
  {
    super(server, entity);
  }
  
  public EntitySilverfish getHandle()
  {
    return (EntitySilverfish)this.entity;
  }
  
  public String toString()
  {
    return "CraftSilverfish";
  }
  
  public EntityType getType() {
    return EntityType.SILVERFISH;
  }
}
