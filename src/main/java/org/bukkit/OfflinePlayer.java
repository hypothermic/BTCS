package org.bukkit;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.ServerOperator;

public abstract interface OfflinePlayer
  extends ServerOperator, AnimalTamer, ConfigurationSerializable
{
  public abstract boolean isOnline();
  
  public abstract String getName();
  
  public abstract boolean isBanned();
  
  public abstract void setBanned(boolean paramBoolean);
  
  public abstract boolean isWhitelisted();
  
  public abstract void setWhitelisted(boolean paramBoolean);
  
  public abstract Player getPlayer();
  
  public abstract long getFirstPlayed();
  
  public abstract long getLastPlayed();
  
  public abstract boolean hasPlayedBefore();
  
  public abstract Location getBedSpawnLocation();
}
