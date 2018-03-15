package org.bukkit.craftbukkit.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;



public class CraftTask
  implements Comparable<Object>, BukkitTask
{
  private final Runnable task;
  private final boolean syncTask;
  private long executionTick;
  private final long period;
  private final Plugin owner;
  private final int idNumber;
  private static Integer idCounter = Integer.valueOf(1);
  private static Object idCounterSync = new Object();
  
  CraftTask(Plugin owner, Runnable task, boolean syncTask) {
    this(owner, task, syncTask, -1L, -1L);
  }
  
  CraftTask(Plugin owner, Runnable task, boolean syncTask, long executionTick) {
    this(owner, task, syncTask, executionTick, -1L);
  }
  
  CraftTask(Plugin owner, Runnable task, boolean syncTask, long executionTick, long period) {
    this.task = task;
    this.syncTask = syncTask;
    this.executionTick = executionTick;
    this.period = period;
    this.owner = owner;
    this.idNumber = getNextId();
  }
  
  static int getNextId() {
    synchronized (idCounterSync) {
      Integer localInteger1 = idCounter;Integer localInteger2 = idCounter = Integer.valueOf(idCounter.intValue() + 1);
      return idCounter.intValue();
    }
  }
  
  Runnable getTask() {
    return this.task;
  }
  
  public boolean isSync() {
    return this.syncTask;
  }
  
  long getExecutionTick() {
    return this.executionTick;
  }
  
  long getPeriod() {
    return this.period;
  }
  
  public Plugin getOwner() {
    return this.owner;
  }
  
  void updateExecution() {
    this.executionTick += this.period;
  }
  
  public int getTaskId() {
    return getIdNumber();
  }
  
  int getIdNumber() {
    return this.idNumber;
  }
  
  public int compareTo(Object other) {
    if (!(other instanceof CraftTask)) {
      return 0;
    }
    CraftTask o = (CraftTask)other;
    long timeDiff = this.executionTick - o.getExecutionTick();
    if (timeDiff > 0L)
      return 1;
    if (timeDiff < 0L) {
      return -1;
    }
    CraftTask otherCraftTask = (CraftTask)other;
    return getIdNumber() - otherCraftTask.getIdNumber();
  }
  



  public boolean equals(Object other)
  {
    if (other == null) {
      return false;
    }
    
    if (!(other instanceof CraftTask)) {
      return false;
    }
    
    CraftTask otherCraftTask = (CraftTask)other;
    return otherCraftTask.getIdNumber() == getIdNumber();
  }
  
  public int hashCode()
  {
    return getIdNumber();
  }
}
