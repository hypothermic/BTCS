package org.bukkit.event.entity;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class EntityDamageByBlockEvent
  extends EntityDamageEvent
{
  private final Block damager;
  
  public EntityDamageByBlockEvent(Block damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage)
  {
    super(damagee, cause, damage);
    this.damager = damager;
  }
  




  public Block getDamager()
  {
    return this.damager;
  }
}
