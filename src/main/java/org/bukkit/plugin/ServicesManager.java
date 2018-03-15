package org.bukkit.plugin;

import java.util.Collection;
import java.util.List;

public abstract interface ServicesManager
{
  public abstract <T> void register(Class<T> paramClass, T paramT, Plugin paramPlugin, ServicePriority paramServicePriority);
  
  public abstract void unregisterAll(Plugin paramPlugin);
  
  public abstract void unregister(Class<?> paramClass, Object paramObject);
  
  public abstract void unregister(Object paramObject);
  
  public abstract <T> T load(Class<T> paramClass);
  
  public abstract <T> RegisteredServiceProvider<T> getRegistration(Class<T> paramClass);
  
  public abstract List<RegisteredServiceProvider<?>> getRegistrations(Plugin paramPlugin);
  
  public abstract <T> Collection<RegisteredServiceProvider<T>> getRegistrations(Class<T> paramClass);
  
  public abstract Collection<Class<?>> getKnownServices();
  
  public abstract <T> boolean isProvidedFor(Class<T> paramClass);
}
