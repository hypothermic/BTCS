package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityIronGolem;
import org.bukkit.entity.EntityType;

public class CraftIronGolem extends CraftGolem implements org.bukkit.entity.IronGolem
{
  public CraftIronGolem(org.bukkit.craftbukkit.CraftServer server, EntityIronGolem entity)
  {
    super(server, entity);
  }
  
  public EntityIronGolem getHandle()
  {
    return (EntityIronGolem)this.entity;
  }
  
  public String toString()
  {
    return "CraftIronGolem";
  }
  
  public boolean isPlayerCreated() {
    return getHandle().n_();
  }
  
  public void setPlayerCreated(boolean playerCreated) {
    getHandle().b(playerCreated);
  }
  
  public EntityType getType()
  {
    return EntityType.IRON_GOLEM;
  }
}
