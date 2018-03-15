package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityComplex;

public abstract class CraftComplexLivingEntity extends CraftLivingEntity implements org.bukkit.entity.ComplexLivingEntity
{
  public CraftComplexLivingEntity(org.bukkit.craftbukkit.CraftServer server, EntityComplex entity)
  {
    super(server, entity);
  }
  
  public EntityComplex getHandle()
  {
    return (EntityComplex)this.entity;
  }
  
  public String toString()
  {
    return "CraftComplexLivingEntity";
  }
}
