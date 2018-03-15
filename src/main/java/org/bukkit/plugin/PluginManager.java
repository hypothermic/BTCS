package org.bukkit.plugin;

import java.io.File;
import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;

public abstract interface PluginManager
{
  public abstract void registerInterface(Class<? extends PluginLoader> paramClass)
    throws IllegalArgumentException;
  
  public abstract Plugin getPlugin(String paramString);
  
  public abstract Plugin[] getPlugins();
  
  public abstract boolean isPluginEnabled(String paramString);
  
  public abstract boolean isPluginEnabled(Plugin paramPlugin);
  
  public abstract Plugin loadPlugin(File paramFile)
    throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException;
  
  public abstract Plugin[] loadPlugins(File paramFile);
  
  public abstract void disablePlugins();
  
  public abstract void clearPlugins();
  
  public abstract void callEvent(Event paramEvent)
    throws IllegalStateException;
  
  public abstract void registerEvents(Listener paramListener, Plugin paramPlugin);
  
  public abstract void registerEvent(Class<? extends Event> paramClass, Listener paramListener, EventPriority paramEventPriority, EventExecutor paramEventExecutor, Plugin paramPlugin);
  
  public abstract void registerEvent(Class<? extends Event> paramClass, Listener paramListener, EventPriority paramEventPriority, EventExecutor paramEventExecutor, Plugin paramPlugin, boolean paramBoolean);
  
  public abstract void enablePlugin(Plugin paramPlugin);
  
  public abstract void disablePlugin(Plugin paramPlugin);
  
  public abstract Permission getPermission(String paramString);
  
  public abstract void addPermission(Permission paramPermission);
  
  public abstract void removePermission(Permission paramPermission);
  
  public abstract void removePermission(String paramString);
  
  public abstract Set<Permission> getDefaultPermissions(boolean paramBoolean);
  
  public abstract void recalculatePermissionDefaults(Permission paramPermission);
  
  public abstract void subscribeToPermission(String paramString, Permissible paramPermissible);
  
  public abstract void unsubscribeFromPermission(String paramString, Permissible paramPermissible);
  
  public abstract Set<Permissible> getPermissionSubscriptions(String paramString);
  
  public abstract void subscribeToDefaultPerms(boolean paramBoolean, Permissible paramPermissible);
  
  public abstract void unsubscribeFromDefaultPerms(boolean paramBoolean, Permissible paramPermissible);
  
  public abstract Set<Permissible> getDefaultPermSubscriptions(boolean paramBoolean);
  
  public abstract Set<Permission> getPermissions();
  
  public abstract boolean useTimings();
}
