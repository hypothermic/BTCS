package org.bukkit.craftbukkit.scheduler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

public class CraftScheduler implements org.bukkit.scheduler.BukkitScheduler, Runnable
{
  private static final Logger logger = Logger.getLogger("Minecraft");
  
  private final CraftThreadManager craftThreadManager = new CraftThreadManager();
  
  private final LinkedList<CraftTask> mainThreadQueue = new LinkedList();
  private final LinkedList<CraftTask> syncedTasks = new LinkedList();
  
  private final TreeMap<CraftTask, Boolean> schedulerQueue = new TreeMap();
  
  private Long currentTick = Long.valueOf(0L);
  

  private final Lock mainThreadLock = new ReentrantLock();
  private final Lock syncedTasksLock = new ReentrantLock();
  
  public CraftScheduler() {
    Thread t = new Thread(this);
    t.start();
  }
  
  public void run()
  {
    for (;;) {
      boolean stop = false;
      long firstTick = -1L;
      long currentTick = -1L;
      CraftTask first = null;
      do {
        synchronized (this.schedulerQueue) {
          first = null;
          if (!this.schedulerQueue.isEmpty()) {
            first = (CraftTask)this.schedulerQueue.firstKey();
            if (first != null) {
              currentTick = getCurrentTick();
              
              firstTick = first.getExecutionTick();
              
              if (currentTick >= firstTick) {
                this.schedulerQueue.remove(first);
                processTask(first);
                if (first.getPeriod() >= 0L) {
                  first.updateExecution();
                  this.schedulerQueue.put(first, Boolean.valueOf(first.isSync()));
                }
              } else {
                stop = true;
              }
            } else {
              stop = true;
            }
          } else {
            stop = true;
          }
        }
      } while (!stop);
      
      long sleepTime = 0L;
      if (first == null) {
        sleepTime = 60000L;
      } else {
        currentTick = getCurrentTick();
        sleepTime = (firstTick - currentTick) * 50L + 25L;
      }
      
      if (sleepTime < 50L) {
        sleepTime = 50L;
      } else if (sleepTime > 60000L) {
        sleepTime = 60000L;
      }
      
      synchronized (this.schedulerQueue) {
        try {
          this.schedulerQueue.wait(sleepTime);
        } catch (InterruptedException ie) {}
      }
    }
  }
  
  void processTask(CraftTask task) {
    if (task.isSync()) {
      addToMainThreadQueue(task);
    } else {
      this.craftThreadManager.executeTask(task.getTask(), task.getOwner(), task.getIdNumber());
    }
  }
  
  public void mainThreadHeartbeat(long currentTick)
  {
    if (this.syncedTasksLock.tryLock()) {
      try {
        if (this.mainThreadLock.tryLock()) {
          try {
            this.currentTick = Long.valueOf(currentTick);
            while (!this.mainThreadQueue.isEmpty()) {
              this.syncedTasks.addLast(this.mainThreadQueue.removeFirst());
            }
          } finally {
            this.mainThreadLock.unlock();
          }
        }
        long breakTime = System.currentTimeMillis() + 35L;
        while ((!this.syncedTasks.isEmpty()) && (System.currentTimeMillis() <= breakTime)) {
          CraftTask task = (CraftTask)this.syncedTasks.removeFirst();
          try {
            task.getTask().run();
          }
          catch (Throwable t) {
            logger.log(Level.WARNING, "Task of '" + task.getOwner().getDescription().getName() + "' generated an exception", t);
            synchronized (this.schedulerQueue) {
              this.schedulerQueue.remove(task);
            }
          }
        }
      } finally {
        this.syncedTasksLock.unlock();
      }
    }
  }
  
  long getCurrentTick() {
    this.mainThreadLock.lock();
    long tempTick = 0L;
    try {
      tempTick = this.currentTick.longValue();
    } finally {
      this.mainThreadLock.unlock();
    }
    return tempTick;
  }
  
  void addToMainThreadQueue(CraftTask task) {
    this.mainThreadLock.lock();
    try {
      this.mainThreadQueue.addLast(task);
    } finally {
      this.mainThreadLock.unlock();
    }
  }
  
  void wipeSyncedTasks() {
    this.syncedTasksLock.lock();
    try {
      this.syncedTasks.clear();
    } finally {
      this.syncedTasksLock.unlock();
    }
  }
  
  void wipeMainThreadQueue() {
    this.mainThreadLock.lock();
    try {
      this.mainThreadQueue.clear();
    } finally {
      this.mainThreadLock.unlock();
    }
  }
  
  public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
    return scheduleSyncRepeatingTask(plugin, task, delay, -1L);
  }
  
  public int scheduleSyncDelayedTask(Plugin plugin, Runnable task) {
    return scheduleSyncDelayedTask(plugin, task, 0L);
  }
  
  public int scheduleSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }
    if (delay < 0L) {
      throw new IllegalArgumentException("Delay cannot be less than 0");
    }
    
    CraftTask newTask = new CraftTask(plugin, task, true, getCurrentTick() + delay, period);
    
    synchronized (this.schedulerQueue) {
      this.schedulerQueue.put(newTask, Boolean.valueOf(true));
      this.schedulerQueue.notify();
    }
    return newTask.getIdNumber();
  }
  
  public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
    return scheduleAsyncRepeatingTask(plugin, task, delay, -1L);
  }
  
  public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) {
    return scheduleAsyncDelayedTask(plugin, task, 0L);
  }
  
  public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
    if (plugin == null) {
      throw new IllegalArgumentException("Plugin cannot be null");
    }
    if (task == null) {
      throw new IllegalArgumentException("Task cannot be null");
    }
    if (delay < 0L) {
      throw new IllegalArgumentException("Delay cannot be less than 0");
    }
    
    CraftTask newTask = new CraftTask(plugin, task, false, getCurrentTick() + delay, period);
    
    synchronized (this.schedulerQueue) {
      this.schedulerQueue.put(newTask, Boolean.valueOf(false));
      this.schedulerQueue.notify();
    }
    return newTask.getIdNumber();
  }
  
  public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
    CraftFuture<T> craftFuture = new CraftFuture(this, task);
    synchronized (craftFuture) {
      int taskId = scheduleSyncDelayedTask(plugin, craftFuture);
      craftFuture.setTaskId(taskId);
    }
    return craftFuture;
  }
  
  public void cancelTask(int taskId) {
    this.syncedTasksLock.lock();
    try {
      synchronized (this.schedulerQueue) {
        this.mainThreadLock.lock();
        try {
          Iterator<CraftTask> itr = this.schedulerQueue.keySet().iterator();
          while (itr.hasNext()) {
            CraftTask current = (CraftTask)itr.next();
            if (current.getIdNumber() == taskId) {
              itr.remove();
            }
          }
          itr = this.mainThreadQueue.iterator();
          while (itr.hasNext()) {
            CraftTask current = (CraftTask)itr.next();
            if (current.getIdNumber() == taskId) {
              itr.remove();
            }
          }
          itr = this.syncedTasks.iterator();
          while (itr.hasNext()) {
            CraftTask current = (CraftTask)itr.next();
            if (current.getIdNumber() == taskId) {
              itr.remove();
            }
          }
        } finally {
          this.mainThreadLock.unlock();
        }
      }
    } finally {
      this.syncedTasksLock.unlock();
    }
    
    this.craftThreadManager.interruptTask(taskId);
  }
  
  public void cancelTasks(Plugin plugin) {
    this.syncedTasksLock.lock();
    try {
      synchronized (this.schedulerQueue) {
        this.mainThreadLock.lock();
        try {
          Iterator<CraftTask> itr = this.schedulerQueue.keySet().iterator();
          while (itr.hasNext()) {
            CraftTask current = (CraftTask)itr.next();
            if (current.getOwner().equals(plugin)) {
              itr.remove();
            }
          }
          itr = this.mainThreadQueue.iterator();
          while (itr.hasNext()) {
            CraftTask current = (CraftTask)itr.next();
            if (current.getOwner().equals(plugin)) {
              itr.remove();
            }
          }
          itr = this.syncedTasks.iterator();
          while (itr.hasNext()) {
            CraftTask current = (CraftTask)itr.next();
            if (current.getOwner().equals(plugin)) {
              itr.remove();
            }
          }
        } finally {
          this.mainThreadLock.unlock();
        }
      }
    } finally {
      this.syncedTasksLock.unlock();
    }
    
    this.craftThreadManager.interruptTasks(plugin);
  }
  
  public void cancelAllTasks() {
    synchronized (this.schedulerQueue) {
      this.schedulerQueue.clear();
    }
    wipeMainThreadQueue();
    wipeSyncedTasks();
    
    this.craftThreadManager.interruptAllTasks();
  }
  
  public boolean isCurrentlyRunning(int taskId) {
    return this.craftThreadManager.isAlive(taskId);
  }
  
  public boolean isQueued(int taskId) {
    synchronized (this.schedulerQueue) {
      Iterator<CraftTask> itr = this.schedulerQueue.keySet().iterator();
      while (itr.hasNext()) {
        CraftTask current = (CraftTask)itr.next();
        if (current.getIdNumber() == taskId) {
          return true;
        }
      }
      return false;
    }
  }
  
  public List<BukkitWorker> getActiveWorkers() {
    synchronized (this.craftThreadManager.workers) {
      List<BukkitWorker> workerList = new ArrayList(this.craftThreadManager.workers.size());
      Iterator<CraftWorker> itr = this.craftThreadManager.workers.iterator();
      
      while (itr.hasNext()) {
        workerList.add((BukkitWorker)itr.next());
      }
      return workerList;
    }
  }
  
  public List<BukkitTask> getPendingTasks() {
    List<CraftTask> taskList = null;
    this.syncedTasksLock.lock();
    try {
      synchronized (this.schedulerQueue) {
        this.mainThreadLock.lock();
        try {
          taskList = new ArrayList(this.mainThreadQueue.size() + this.syncedTasks.size() + this.schedulerQueue.size());
          taskList.addAll(this.mainThreadQueue);
          taskList.addAll(this.syncedTasks);
          taskList.addAll(this.schedulerQueue.keySet());
        } finally {
          this.mainThreadLock.unlock();
        }
      }
    } finally {
      this.syncedTasksLock.unlock();
    }
    List<BukkitTask> newTaskList = new ArrayList(taskList.size());
    
    for (CraftTask craftTask : taskList) {
      newTaskList.add(craftTask);
    }
    return newTaskList;
  }
}
