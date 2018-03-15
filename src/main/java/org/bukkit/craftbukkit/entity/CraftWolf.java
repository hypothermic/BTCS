package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityWolf;
import org.bukkit.entity.EntityType;

public class CraftWolf extends CraftTameableAnimal implements org.bukkit.entity.Wolf
{
  public CraftWolf(org.bukkit.craftbukkit.CraftServer server, EntityWolf wolf)
  {
    super(server, wolf);
  }
  
  public boolean isAngry() {
    return getHandle().isAngry();
  }
  
  public void setAngry(boolean angry) {
    getHandle().setAngry(angry);
  }
  
  public EntityWolf getHandle()
  {
    return (EntityWolf)this.entity;
  }
  
  public EntityType getType()
  {
    return EntityType.WOLF;
  }
}
