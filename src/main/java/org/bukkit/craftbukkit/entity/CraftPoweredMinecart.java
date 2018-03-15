package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityMinecart;
import org.bukkit.craftbukkit.CraftServer;

public class CraftPoweredMinecart extends CraftMinecart implements org.bukkit.entity.PoweredMinecart
{
  public CraftPoweredMinecart(CraftServer server, EntityMinecart entity)
  {
    super(server, entity);
  }
  
  public String toString()
  {
    return "CraftPoweredMinecart";
  }
}
