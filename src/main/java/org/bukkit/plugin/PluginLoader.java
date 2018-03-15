package org.bukkit.plugin;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public abstract interface PluginLoader
{
  public abstract Plugin loadPlugin(File paramFile)
    throws InvalidPluginException, UnknownDependencyException;
  
  public abstract PluginDescriptionFile getPluginDescription(File paramFile)
    throws InvalidDescriptionException;
  
  public abstract Pattern[] getPluginFileFilters();
  
  public abstract Map<Class<? extends Event>, Set<RegisteredListener>> createRegisteredListeners(Listener paramListener, Plugin paramPlugin);
  
  public abstract void enablePlugin(Plugin paramPlugin);
  
  public abstract void disablePlugin(Plugin paramPlugin);
}
