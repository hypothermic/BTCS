package org.bukkit.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.bukkit.plugin.Plugin;

public abstract interface BukkitScheduler
{
  public abstract int scheduleSyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong);
  
  public abstract int scheduleSyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable);
  
  public abstract int scheduleSyncRepeatingTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  public abstract int scheduleAsyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong);
  
  public abstract int scheduleAsyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable);
  
  public abstract int scheduleAsyncRepeatingTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  public abstract <T> Future<T> callSyncMethod(Plugin paramPlugin, Callable<T> paramCallable);
  
  public abstract void cancelTask(int paramInt);
  
  public abstract void cancelTasks(Plugin paramPlugin);
  
  public abstract void cancelAllTasks();
  
  public abstract boolean isCurrentlyRunning(int paramInt);
  
  public abstract boolean isQueued(int paramInt);
  
  public abstract List<BukkitWorker> getActiveWorkers();
  
  public abstract List<BukkitTask> getPendingTasks();
}
