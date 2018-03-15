package org.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;

public abstract interface EventExecutor
{
  public abstract void execute(Listener paramListener, Event paramEvent)
    throws EventException;
}
