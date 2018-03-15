package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityAnimal;
import org.bukkit.craftbukkit.CraftServer;

public class CraftAnimals extends CraftAgeable implements org.bukkit.entity.Animals
{
  public CraftAnimals(CraftServer server, EntityAnimal entity)
  {
    super(server, entity);
  }
  
  public EntityAnimal getHandle()
  {
    return (EntityAnimal)this.entity;
  }
  
  public String toString()
  {
    return "CraftAnimals";
  }
}
