package org.bukkit.event.vehicle;

import org.bukkit.event.HandlerList;

public class VehicleUpdateEvent extends VehicleEvent
{
  private static final HandlerList handlers = new HandlerList();
  
  public VehicleUpdateEvent(org.bukkit.entity.Vehicle vehicle) {
    super(vehicle);
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
