package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public abstract interface BukkitWorker
{
  public abstract int getTaskId();
  
  public abstract Plugin getOwner();
  
  public abstract Thread getThread();
}
