package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntitySheep;
import org.bukkit.DyeColor;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;

public class CraftSheep extends CraftAnimals implements org.bukkit.entity.Sheep
{
  public CraftSheep(CraftServer server, EntitySheep entity)
  {
    super(server, entity);
  }
  
  public DyeColor getColor() {
    return DyeColor.getByData((byte)getHandle().getColor());
  }
  
  public void setColor(DyeColor color) {
    getHandle().setColor(color.getData());
  }
  
  public boolean isSheared() {
    return getHandle().isSheared();
  }
  
  public void setSheared(boolean flag) {
    getHandle().setSheared(flag);
  }
  
  public EntitySheep getHandle()
  {
    return (EntitySheep)this.entity;
  }
  
  public String toString()
  {
    return "CraftSheep";
  }
  
  public EntityType getType() {
    return EntityType.SHEEP;
  }
}
