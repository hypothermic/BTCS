package org.bukkit.event.inventory;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.inventory.BrewerInventory;

public class BrewEvent extends BlockEvent implements org.bukkit.event.Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private BrewerInventory contents;
  private boolean cancelled;
  
  public BrewEvent(Block brewer, BrewerInventory contents) {
    super(brewer);
    this.contents = contents;
  }
  
  public BrewerInventory getContents() {
    return this.contents;
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
