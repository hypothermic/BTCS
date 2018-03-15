package org.bukkit.craftbukkit.scheduler;

import java.util.HashSet;
import java.util.Iterator;
import org.bukkit.plugin.Plugin;


public class CraftThreadManager
{
  final HashSet<CraftWorker> workers = new HashSet();
  
  void executeTask(Runnable task, Plugin owner, int taskId)
  {
    CraftWorker craftWorker = new CraftWorker(this, task, owner, taskId);
    synchronized (this.workers) {
      this.workers.add(craftWorker);
    }
  }
  
  void interruptTask(int taskId)
  {
    synchronized (this.workers) {
      Iterator<CraftWorker> itr = this.workers.iterator();
      while (itr.hasNext()) {
        CraftWorker craftWorker = (CraftWorker)itr.next();
        if (craftWorker.getTaskId() == taskId) {
          craftWorker.interrupt();
        }
      }
    }
  }
  
  void interruptTasks(Plugin owner) {
    synchronized (this.workers) {
      Iterator<CraftWorker> itr = this.workers.iterator();
      while (itr.hasNext()) {
        CraftWorker craftWorker = (CraftWorker)itr.next();
        if (craftWorker.getOwner().equals(owner)) {
          craftWorker.interrupt();
        }
      }
    }
  }
  
  void interruptAllTasks() {
    synchronized (this.workers) {
      Iterator<CraftWorker> itr = this.workers.iterator();
      while (itr.hasNext()) {
        CraftWorker craftWorker = (CraftWorker)itr.next();
        craftWorker.interrupt();
      }
    }
  }
  
  boolean isAlive(int taskId) {
    synchronized (this.workers) {
      Iterator<CraftWorker> itr = this.workers.iterator();
      while (itr.hasNext()) {
        CraftWorker craftWorker = (CraftWorker)itr.next();
        if (craftWorker.getTaskId() == taskId) {
          return craftWorker.isAlive();
        }
      }
      
      return false;
    }
  }
}
