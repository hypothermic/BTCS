package org.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;


public class TimedRegisteredListener
  extends RegisteredListener
{
  private int count;
  private long totalTime;
  private Event event;
  private boolean multiple = false;
  
  public TimedRegisteredListener(Listener pluginListener, EventExecutor eventExecutor, EventPriority eventPriority, Plugin registeredPlugin, boolean listenCancelled) {
    super(pluginListener, eventExecutor, eventPriority, registeredPlugin, listenCancelled);
  }
  
  public void callEvent(Event event) throws EventException
  {
    if (event.isAsynchronous()) {
      super.callEvent(event);
      return;
    }
    this.count += 1;
    if (this.event == null) {
      this.event = event;
    }
    else if (!this.event.getClass().equals(event.getClass())) {
      this.multiple = true;
    }
    long start = System.nanoTime();
    super.callEvent(event);
    this.totalTime += System.nanoTime() - start;
  }
  


  public void reset()
  {
    this.count = 0;
    this.totalTime = 0L;
  }
  




  public int getCount()
  {
    return this.count;
  }
  




  public long getTotalTime()
  {
    return this.totalTime;
  }
  




  public Event getEvent()
  {
    return this.event;
  }
  




  public boolean hasMultiple()
  {
    return this.multiple;
  }
}
