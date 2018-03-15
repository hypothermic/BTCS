package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EntityDamageEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private int damage;
  private boolean cancelled;
  private final DamageCause cause;
  
  public EntityDamageEvent(Entity damagee, DamageCause cause, int damage) {
    super(damagee);
    this.cause = cause;
    this.damage = damage;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  




  public int getDamage()
  {
    return this.damage;
  }
  




  public void setDamage(int damage)
  {
    this.damage = damage;
  }
  




  public DamageCause getCause()
  {
    return this.cause;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  








  public static enum DamageCause
  {
    CONTACT, 
    




    ENTITY_ATTACK, 
    




    PROJECTILE, 
    




    SUFFOCATION, 
    




    FALL, 
    




    FIRE, 
    




    FIRE_TICK, 
    




    MELTING, 
    




    LAVA, 
    




    DROWNING, 
    




    BLOCK_EXPLOSION, 
    




    ENTITY_EXPLOSION, 
    




    VOID, 
    




    LIGHTNING, 
    




    SUICIDE, 
    




    STARVATION, 
    




    POISON, 
    




    MAGIC, 
    




    CUSTOM;
    
    private DamageCause() {}
  }
}
