package org.bukkit.event.world;

import org.bukkit.event.HandlerList;

public class WorldSaveEvent extends WorldEvent
{
  private static final HandlerList handlers = new HandlerList();
  
  public WorldSaveEvent(org.bukkit.World world) {
    super(world);
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
