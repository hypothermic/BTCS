package org.bukkit.craftbukkit.entity;

import org.bukkit.craftbukkit.CraftServer;

public abstract class AbstractProjectile extends CraftEntity implements org.bukkit.entity.Projectile
{
  private boolean doesBounce;
  
  public AbstractProjectile(CraftServer server, net.minecraft.server.Entity entity)
  {
    super(server, entity);
    this.doesBounce = false;
  }
  
  public boolean doesBounce() {
    return this.doesBounce;
  }
  
  public void setBounce(boolean doesBounce) {
    this.doesBounce = doesBounce;
  }
}
