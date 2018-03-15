package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityWeatherLighting;
import org.bukkit.entity.EntityType;

public class CraftLightningStrike extends CraftEntity implements org.bukkit.entity.LightningStrike
{
  public CraftLightningStrike(org.bukkit.craftbukkit.CraftServer server, EntityWeatherLighting entity)
  {
    super(server, entity);
  }
  
  public boolean isEffect() {
    return ((EntityWeatherLighting)super.getHandle()).isEffect;
  }
  
  public EntityWeatherLighting getHandle()
  {
    return (EntityWeatherLighting)this.entity;
  }
  
  public String toString()
  {
    return "CraftLightningStrike";
  }
  
  public EntityType getType() {
    return EntityType.LIGHTNING;
  }
}
