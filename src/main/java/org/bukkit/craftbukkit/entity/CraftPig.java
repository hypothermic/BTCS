package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityPig;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftPig extends CraftAnimals implements org.bukkit.entity.Pig
{
  public CraftPig(CraftServer server, EntityPig entity)
  {
    super(server, entity);
  }
  
  public boolean hasSaddle() {
    return getHandle().hasSaddle();
  }
  
  public void setSaddle(boolean saddled) {
    getHandle().setSaddle(saddled);
  }
  
  public EntityPig getHandle() {
    return (EntityPig)this.entity;
  }
  
  public String toString()
  {
    return "CraftPig";
  }
  
  public EntityType getType() {
    return EntityType.PIG;
  }
}
