package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityChicken;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

public class CraftChicken extends CraftAnimals implements Chicken
{
  public CraftChicken(CraftServer server, EntityChicken entity)
  {
    super(server, entity);
  }
  
  public EntityChicken getHandle()
  {
    return (EntityChicken)this.entity;
  }
  
  public String toString()
  {
    return "CraftChicken";
  }
  
  public EntityType getType() {
    return EntityType.CHICKEN;
  }
}
