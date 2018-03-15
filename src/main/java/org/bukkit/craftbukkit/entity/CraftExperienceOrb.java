package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityExperienceOrb;
import org.bukkit.entity.EntityType;

public class CraftExperienceOrb extends CraftEntity implements org.bukkit.entity.ExperienceOrb
{
  public CraftExperienceOrb(org.bukkit.craftbukkit.CraftServer server, EntityExperienceOrb entity)
  {
    super(server, entity);
  }
  
  public int getExperience() {
    return getHandle().value;
  }
  
  public void setExperience(int value) {
    getHandle().value = value;
  }
  
  public EntityExperienceOrb getHandle()
  {
    return (EntityExperienceOrb)this.entity;
  }
  
  public String toString()
  {
    return "CraftExperienceOrb";
  }
  
  public EntityType getType() {
    return EntityType.EXPERIENCE_ORB;
  }
}
