package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ItemSpawnEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final Location location;
  private boolean canceled;
  
  public ItemSpawnEvent(Item spawnee, Location loc) {
    super(spawnee);
    this.location = loc;
  }
  
  public boolean isCancelled() {
    return this.canceled;
  }
  
  public void setCancelled(boolean cancel) {
    this.canceled = cancel;
  }
  
  public Item getEntity()
  {
    return (Item)this.entity;
  }
  




  public Location getLocation()
  {
    return this.location;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
