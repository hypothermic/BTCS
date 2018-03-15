package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

public abstract class CraftVehicle extends CraftEntity implements org.bukkit.entity.Vehicle
{
  public CraftVehicle(CraftServer server, net.minecraft.server.Entity entity) {
    super(server, entity);
  }
  
  public String toString()
  {
    return "CraftVehicle{passenger=" + getPassenger() + '}';
  }
}
