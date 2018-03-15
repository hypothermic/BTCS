package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityEgg;
import org.bukkit.entity.EntityType;

public class CraftEgg extends CraftProjectile implements org.bukkit.entity.Egg
{
  public CraftEgg(org.bukkit.craftbukkit.CraftServer server, EntityEgg entity)
  {
    super(server, entity);
  }
  
  public EntityEgg getHandle()
  {
    return (EntityEgg)this.entity;
  }
  
  public String toString()
  {
    return "CraftEgg";
  }
  
  public EntityType getType() {
    return EntityType.EGG;
  }
}
