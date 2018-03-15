package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class PlayerRespawnEvent extends PlayerEvent
{
  private static final HandlerList handlers = new HandlerList();
  private Location respawnLocation;
  private final boolean isBedSpawn;
  
  public PlayerRespawnEvent(org.bukkit.entity.Player respawnPlayer, Location respawnLocation, boolean isBedSpawn) {
    super(respawnPlayer);
    this.respawnLocation = respawnLocation;
    this.isBedSpawn = isBedSpawn;
  }
  




  public Location getRespawnLocation()
  {
    return this.respawnLocation;
  }
  




  public void setRespawnLocation(Location respawnLocation)
  {
    this.respawnLocation = respawnLocation;
  }
  




  public boolean isBedSpawn()
  {
    return this.isBedSpawn;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
