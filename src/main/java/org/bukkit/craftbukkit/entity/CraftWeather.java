package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityWeather;
import org.bukkit.entity.EntityType;

public class CraftWeather extends CraftEntity implements org.bukkit.entity.Weather
{
  public CraftWeather(org.bukkit.craftbukkit.CraftServer server, EntityWeather entity)
  {
    super(server, entity);
  }
  
  public EntityWeather getHandle()
  {
    return (EntityWeather)this.entity;
  }
  
  public String toString()
  {
    return "CraftWeather";
  }
  
  public EntityType getType() {
    return EntityType.WEATHER;
  }
}
