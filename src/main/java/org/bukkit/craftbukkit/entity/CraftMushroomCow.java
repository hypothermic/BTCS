package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityMushroomCow;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftMushroomCow extends CraftCow implements org.bukkit.entity.MushroomCow
{
  public CraftMushroomCow(CraftServer server, EntityMushroomCow entity)
  {
    super(server, entity);
  }
  
  public EntityMushroomCow getHandle()
  {
    return (EntityMushroomCow)this.entity;
  }
  
  public String toString()
  {
    return "CraftMushroomCow";
  }
  
  public EntityType getType() {
    return EntityType.MUSHROOM_COW;
  }
}
