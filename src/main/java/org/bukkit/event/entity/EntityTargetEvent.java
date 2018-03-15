package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EntityTargetEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancel = false;
  private Entity target;
  private final TargetReason reason;
  
  public EntityTargetEvent(Entity entity, Entity target, TargetReason reason) {
    super(entity);
    this.target = target;
    this.reason = reason;
  }
  
  public boolean isCancelled() {
    return this.cancel;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  




  public TargetReason getReason()
  {
    return this.reason;
  }
  






  public Entity getTarget()
  {
    return this.target;
  }
  










  public void setTarget(Entity target)
  {
    this.target = target;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  






  public static enum TargetReason
  {
    TARGET_DIED, 
    



    CLOSEST_PLAYER, 
    


    TARGET_ATTACKED_ENTITY, 
    



    PIG_ZOMBIE_TARGET, 
    



    FORGOT_TARGET, 
    


    TARGET_ATTACKED_OWNER, 
    


    OWNER_ATTACKED_TARGET, 
    


    RANDOM_TARGET, 
    


    DEFEND_VILLAGE, 
    


    CUSTOM;
    
    private TargetReason() {}
  }
}
