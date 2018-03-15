package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityComplexPart;
import org.bukkit.entity.ComplexLivingEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class CraftComplexPart extends CraftEntity implements org.bukkit.entity.ComplexEntityPart
{
  public CraftComplexPart(org.bukkit.craftbukkit.CraftServer server, EntityComplexPart entity)
  {
    super(server, entity);
  }
  
  public ComplexLivingEntity getParent() {
    return (ComplexLivingEntity)getHandle().owner.getBukkitEntity();
  }
  
  public void setLastDamageCause(EntityDamageEvent cause)
  {
    getParent().setLastDamageCause(cause);
  }
  
  public EntityDamageEvent getLastDamageCause()
  {
    return getParent().getLastDamageCause();
  }
  
  public EntityComplexPart getHandle()
  {
    return (EntityComplexPart)this.entity;
  }
  
  public String toString()
  {
    return "CraftComplexPart";
  }
  
  public EntityType getType() {
    return EntityType.COMPLEX_PART;
  }
}
