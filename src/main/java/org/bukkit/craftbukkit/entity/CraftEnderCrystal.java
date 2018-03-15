package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityEnderCrystal;
import org.bukkit.entity.EntityType;

public class CraftEnderCrystal extends CraftEntity implements org.bukkit.entity.EnderCrystal
{
  public CraftEnderCrystal(org.bukkit.craftbukkit.CraftServer server, EntityEnderCrystal entity)
  {
    super(server, entity);
  }
  
  public EntityEnderCrystal getHandle()
  {
    return (EntityEnderCrystal)this.entity;
  }
  
  public String toString()
  {
    return "CraftEnderCrystal";
  }
  
  public EntityType getType() {
    return EntityType.ENDER_CRYSTAL;
  }
}
