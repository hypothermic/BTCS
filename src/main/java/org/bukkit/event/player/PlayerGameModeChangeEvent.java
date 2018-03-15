package org.bukkit.event.player;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerGameModeChangeEvent extends PlayerEvent implements org.bukkit.event.Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;
  private final GameMode newGameMode;
  
  public PlayerGameModeChangeEvent(Player player, GameMode newGameMode) {
    super(player);
    this.newGameMode = newGameMode;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  
  public GameMode getNewGameMode() {
    return this.newGameMode;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
