package org.bukkit.event.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerFishEvent
  extends PlayerEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final Entity entity;
  private boolean cancel = false;
  private final State state;
  
  public PlayerFishEvent(Player player, Entity entity, State state) {
    super(player);
    this.entity = entity;
    this.state = state;
  }
  




  public Entity getCaught()
  {
    return this.entity;
  }
  
  public boolean isCancelled() {
    return this.cancel;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  




  public State getState()
  {
    return this.state;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  






  public static enum State
  {
    FISHING, 
    


    CAUGHT_FISH, 
    


    CAUGHT_ENTITY, 
    


    IN_GROUND, 
    


    FAILED_ATTEMPT;
    
    private State() {}
  }
}
