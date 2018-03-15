package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerMoveEvent
  extends PlayerEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancel = false;
  private Location from;
  private Location to;
  
  public PlayerMoveEvent(Player player, Location from, Location to) {
    super(player);
    this.from = from;
    this.to = to;
  }
  









  public boolean isCancelled()
  {
    return this.cancel;
  }
  









  public void setCancelled(boolean cancel)
  {
    this.cancel = cancel;
  }
  




  public Location getFrom()
  {
    return this.from;
  }
  




  public void setFrom(Location from)
  {
    this.from = from;
  }
  




  public Location getTo()
  {
    return this.to;
  }
  




  public void setTo(Location to)
  {
    this.to = to;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
