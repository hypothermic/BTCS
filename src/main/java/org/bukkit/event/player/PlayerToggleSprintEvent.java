package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PlayerToggleSprintEvent
  extends PlayerEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final boolean isSprinting;
  private boolean cancel = false;
  
  public PlayerToggleSprintEvent(Player player, boolean isSprinting) {
    super(player);
    this.isSprinting = isSprinting;
  }
  




  public boolean isSprinting()
  {
    return this.isSprinting;
  }
  
  public boolean isCancelled() {
    return this.cancel;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
