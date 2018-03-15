package org.bukkit.plugin;

import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RegisteredListener
{
  private final Listener listener;
  private final EventPriority priority;
  private final Plugin plugin;
  private final EventExecutor executor;
  private final boolean ignoreCancelled;
  
  public RegisteredListener(Listener listener, EventExecutor executor, EventPriority priority, Plugin plugin, boolean ignoreCancelled)
  {
    this.listener = listener;
    this.priority = priority;
    this.plugin = plugin;
    this.executor = executor;
    this.ignoreCancelled = ignoreCancelled;
  }
  




  public Listener getListener()
  {
    return this.listener;
  }
  




  public Plugin getPlugin()
  {
    return this.plugin;
  }
  




  public EventPriority getPriority()
  {
    return this.priority;
  }
  




  public void callEvent(org.bukkit.event.Event event)
    throws org.bukkit.event.EventException
  {
    if (((event instanceof org.bukkit.event.Cancellable)) && 
      (((org.bukkit.event.Cancellable)event).isCancelled()) && (isIgnoringCancelled())) {
      return;
    }
    
    this.executor.execute(this.listener, event);
  }
  




  public boolean isIgnoringCancelled()
  {
    return this.ignoreCancelled;
  }
}
