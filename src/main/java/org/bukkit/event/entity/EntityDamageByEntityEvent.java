package org.bukkit.event.entity;

import org.bukkit.entity.Entity;

public class EntityDamageByEntityEvent
  extends EntityDamageEvent
{
  private final Entity damager;
  
  public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage)
  {
    super(damagee, cause, damage);
    this.damager = damager;
  }
  




  public Entity getDamager()
  {
    return this.damager;
  }
}
