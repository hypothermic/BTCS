package org.bukkit.craftbukkit.scheduler;

public class ObjectContainer<T>
{
  T object;
  
  public void setObject(T object) {
    this.object = object;
  }
  
  public T getObject() {
    return (T)this.object;
  }
}
