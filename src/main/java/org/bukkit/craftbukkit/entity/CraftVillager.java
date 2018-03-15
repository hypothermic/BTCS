package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityVillager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager; //BTCS
import org.bukkit.entity.Villager.Profession; // BTCS

public class CraftVillager extends CraftAgeable implements org.bukkit.entity.Villager
{
  public CraftVillager(org.bukkit.craftbukkit.CraftServer server, EntityVillager entity)
  {
    super(server, entity);
  }
  
  public EntityVillager getHandle()
  {
    return (EntityVillager)this.entity;
  }
  
  public String toString()
  {
    return "CraftVillager";
  }
  
  public EntityType getType() {
    return EntityType.VILLAGER;
  }
  
  public Villager.Profession getProfession() {
    return Villager.Profession.getProfession(getHandle().getProfession());
  }
  
  public void setProfession(Villager.Profession profession) {
    org.apache.commons.lang3.Validate.notNull(profession); // BTCS
    getHandle().setProfession(profession.getId());
  }
}
