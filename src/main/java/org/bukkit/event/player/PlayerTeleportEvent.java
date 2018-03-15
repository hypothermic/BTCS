package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;


public class PlayerTeleportEvent
  extends PlayerMoveEvent
{
  private static final HandlerList handlers = new HandlerList();
  private TeleportCause cause = TeleportCause.UNKNOWN;
  
  public PlayerTeleportEvent(Player player, Location from, Location to) {
    super(player, from, to);
  }
  
  public PlayerTeleportEvent(Player player, Location from, Location to, TeleportCause cause) {
    this(player, from, to);
  }
  






  public TeleportCause getCause()
  {
    return this.cause;
  }
  


  public static enum TeleportCause
  {
    ENDER_PEARL, 
    


    COMMAND, 
    


    PLUGIN, 
    


    NETHER_PORTAL, 
    


    END_PORTAL, 
    


    UNKNOWN;
    
    private TeleportCause() {}
  }
  
  public HandlerList getHandlers() { return handlers; }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
