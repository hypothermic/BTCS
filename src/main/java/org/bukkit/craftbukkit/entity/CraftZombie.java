package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityZombie;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

public class CraftZombie extends CraftMonster implements Zombie
{
  public CraftZombie(CraftServer server, EntityZombie entity)
  {
    super(server, entity);
  }
  
  public EntityZombie getHandle()
  {
    return (EntityZombie)this.entity;
  }
  
  public String toString()
  {
    return "CraftZombie";
  }
  
  public EntityType getType() {
    return EntityType.ZOMBIE;
  }
}
