package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public abstract interface BukkitTask
{
  public abstract int getTaskId();
  
  public abstract Plugin getOwner();
  
  public abstract boolean isSync();
}
