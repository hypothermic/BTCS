package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityEnderPearl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class CraftEnderPearl extends CraftProjectile implements org.bukkit.entity.EnderPearl
{
  public CraftEnderPearl(org.bukkit.craftbukkit.CraftServer server, EntityEnderPearl entity)
  {
    super(server, entity);
  }
  
  public EntityEnderPearl getHandle()
  {
    return (EntityEnderPearl)this.entity;
  }
  
  public String toString()
  {
    return "CraftEnderPearl";
  }
  
  public EntityType getType() {
    return EntityType.ENDER_PEARL;
  }
}
