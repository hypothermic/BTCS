package org.bukkit.event.painting;

import org.bukkit.entity.Painting;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PaintingBreakEvent
  extends PaintingEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;
  private final RemoveCause cause;
  
  public PaintingBreakEvent(Painting painting, RemoveCause cause) {
    super(painting);
    this.cause = cause;
  }
  




  public RemoveCause getCause()
  {
    return this.cause;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  





  public static enum RemoveCause
  {
    ENTITY, 
    


    FIRE, 
    


    OBSTRUCTION, 
    


    WATER, 
    


    PHYSICS;
    
    private RemoveCause() {}
  }
  
  public HandlerList getHandlers() { return handlers; }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
