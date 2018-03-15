package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityProjectile;
import org.bukkit.entity.LivingEntity;

public abstract class CraftProjectile extends AbstractProjectile implements org.bukkit.entity.Projectile
{
  public CraftProjectile(org.bukkit.craftbukkit.CraftServer server, net.minecraft.server.Entity entity)
  {
    super(server, entity);
  }
  
  public LivingEntity getShooter() {
    if ((getHandle().shooter instanceof EntityLiving)) {
      return (LivingEntity)getHandle().shooter.getBukkitEntity();
    }
    
    return null;
  }
  
  public void setShooter(LivingEntity shooter) {
    if ((shooter instanceof CraftLivingEntity)) {
      getHandle().shooter = ((EntityLiving)((CraftLivingEntity)shooter).entity);
    }
  }
  
  public EntityProjectile getHandle()
  {
    return (EntityProjectile)this.entity;
  }
  
  public String toString()
  {
    return "CraftProjectile";
  }
}
