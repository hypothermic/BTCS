package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;

public abstract class VehicleCollisionEvent
  extends VehicleEvent
{
  public VehicleCollisionEvent(Vehicle vehicle)
  {
    super(vehicle);
  }
}
