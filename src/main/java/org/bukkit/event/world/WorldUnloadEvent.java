package org.bukkit.event.world;

import org.bukkit.World;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class WorldUnloadEvent
  extends WorldEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean isCancelled;
  
  public WorldUnloadEvent(World world) {
    super(world);
  }
  
  public boolean isCancelled() {
    return this.isCancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.isCancelled = cancel;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
