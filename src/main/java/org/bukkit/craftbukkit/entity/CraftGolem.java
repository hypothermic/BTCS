package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityGolem;

public class CraftGolem extends CraftCreature implements org.bukkit.entity.Golem
{
  public CraftGolem(org.bukkit.craftbukkit.CraftServer server, EntityGolem entity)
  {
    super(server, entity);
  }
  
  public EntityGolem getHandle()
  {
    return (EntityGolem)this.entity;
  }
  
  public String toString()
  {
    return "CraftGolem";
  }
}
