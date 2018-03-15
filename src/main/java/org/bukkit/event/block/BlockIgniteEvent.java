package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;



public class BlockIgniteEvent
  extends BlockEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final IgniteCause cause;
  private boolean cancel;
  private final Player thePlayer;
  
  public BlockIgniteEvent(Block theBlock, IgniteCause cause, Player thePlayer) {
    super(theBlock);
    this.cause = cause;
    this.thePlayer = thePlayer;
    this.cancel = false;
  }
  
  public boolean isCancelled() {
    return this.cancel;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  




  public IgniteCause getCause()
  {
    return this.cause;
  }
  




  public Player getPlayer()
  {
    return this.thePlayer;
  }
  






  public static enum IgniteCause
  {
    LAVA, 
    


    FLINT_AND_STEEL, 
    


    SPREAD, 
    


    LIGHTNING, 
    


    FIREBALL;
    
    private IgniteCause() {}
  }
  
  public HandlerList getHandlers() { return handlers; }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
