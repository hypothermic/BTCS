package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityAgeable;
import org.bukkit.craftbukkit.CraftServer;

public class CraftAgeable extends CraftCreature implements org.bukkit.entity.Ageable
{
  public CraftAgeable(CraftServer server, EntityAgeable entity)
  {
    super(server, entity);
  }
  
  public int getAge() {
    return getHandle().getAge();
  }
  
  public void setAge(int age) {
    getHandle().setAge(age);
  }
  
  public void setAgeLock(boolean lock) {
    getHandle().ageLocked = lock;
  }
  
  public boolean getAgeLock() {
    return getHandle().ageLocked;
  }
  
  public void setBaby() {
    if (isAdult()) {
      setAge(41536);
    }
  }
  
  public void setAdult() {
    if (!isAdult()) {
      setAge(0);
    }
  }
  
  public boolean isAdult() {
    return getAge() >= 0;
  }
  
  public boolean canBreed()
  {
    return getAge() == 0;
  }
  
  public void setBreed(boolean breed) {
    if (breed) {
      setAge(0);
    } else if (isAdult()) {
      setAge(6000);
    }
  }
  
  public EntityAgeable getHandle()
  {
    return (EntityAgeable)this.entity;
  }
  
  public String toString()
  {
    return "CraftAgeable";
  }
}
