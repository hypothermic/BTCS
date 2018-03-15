package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EntityRegainHealthEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;
  private int amount;
  private final RegainReason regainReason;
  
  public EntityRegainHealthEvent(Entity entity, int amount, RegainReason regainReason) {
    super(entity);
    this.amount = amount;
    this.regainReason = regainReason;
  }
  




  public int getAmount()
  {
    return this.amount;
  }
  




  public void setAmount(int amount)
  {
    this.amount = amount;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  




  public RegainReason getRegainReason()
  {
    return this.regainReason;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  






  public static enum RegainReason
  {
    REGEN, 
    


    SATIATED, 
    


    EATING, 
    


    ENDER_CRYSTAL, 
    


    MAGIC, 
    


    MAGIC_REGEN, 
    


    CUSTOM;
    
    private RegainReason() {}
  }
}
