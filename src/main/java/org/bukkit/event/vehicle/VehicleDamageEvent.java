package org.bukkit.event.vehicle;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class VehicleDamageEvent
  extends VehicleEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final Entity attacker;
  private int damage;
  private boolean cancelled;
  
  public VehicleDamageEvent(Vehicle vehicle, Entity attacker, int damage) {
    super(vehicle);
    this.attacker = attacker;
    this.damage = damage;
  }
  




  public Entity getAttacker()
  {
    return this.attacker;
  }
  




  public int getDamage()
  {
    return this.damage;
  }
  




  public void setDamage(int damage)
  {
    this.damage = damage;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
