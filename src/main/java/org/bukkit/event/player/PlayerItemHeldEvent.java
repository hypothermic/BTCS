package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;


public class PlayerItemHeldEvent
  extends PlayerEvent
{
  private static final HandlerList handlers = new HandlerList();
  private final int previous;
  private final int current;
  
  public PlayerItemHeldEvent(Player player, int previous, int current) {
    super(player);
    this.previous = previous;
    this.current = current;
  }
  




  public int getPreviousSlot()
  {
    return this.previous;
  }
  




  public int getNewSlot()
  {
    return this.current;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
