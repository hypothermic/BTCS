package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;



public class PlayerCommandPreprocessEvent
  extends PlayerChatEvent
{
  private static final HandlerList handlers = new HandlerList();
  
  public PlayerCommandPreprocessEvent(Player player, String message) {
    super(player, message);
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
