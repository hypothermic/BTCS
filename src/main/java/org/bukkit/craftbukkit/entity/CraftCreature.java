package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityCreature;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.LivingEntity;

public class CraftCreature extends CraftLivingEntity implements org.bukkit.entity.Creature
{
  public CraftCreature(CraftServer server, EntityCreature entity)
  {
    super(server, entity);
  }
  
  public void setTarget(LivingEntity target) {
    EntityCreature entity = getHandle();
    if (target == null) {
      entity.target = null;
    } else if ((target instanceof CraftLivingEntity)) {
      net.minecraft.server.EntityLiving victim = ((CraftLivingEntity)target).getHandle();
      entity.target = victim;
      entity.pathEntity = entity.world.findPath(entity, entity.target, 16.0F, true, false, false, true);
    }
  }
  
  public CraftLivingEntity getTarget() {
    if (getHandle().target == null) return null;
    if (!(getHandle().target instanceof net.minecraft.server.EntityLiving)) { return null;
    }
    return (CraftLivingEntity)getHandle().target.getBukkitEntity();
  }
  
  public EntityCreature getHandle()
  {
    return (EntityCreature)this.entity;
  }
  
  public String toString()
  {
    return "CraftCreature";
  }
}
