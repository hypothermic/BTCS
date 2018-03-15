package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityComplexPart;
import org.bukkit.entity.EnderDragon;

public class CraftEnderDragonPart extends CraftComplexPart implements org.bukkit.entity.EnderDragonPart
{
  public CraftEnderDragonPart(org.bukkit.craftbukkit.CraftServer server, EntityComplexPart entity)
  {
    super(server, entity);
  }
  
  public EnderDragon getParent()
  {
    return (EnderDragon)super.getParent();
  }
  
  public EntityComplexPart getHandle()
  {
    return (EntityComplexPart)this.entity;
  }
  
  public String toString()
  {
    return "CraftEnderDragonPart";
  }
}
