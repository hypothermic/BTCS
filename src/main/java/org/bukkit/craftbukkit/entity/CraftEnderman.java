package org.bukkit.craftbukkit.entity;

import net.minecraft.server.EntityEnderman;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.material.MaterialData;

public class CraftEnderman extends CraftMonster implements org.bukkit.entity.Enderman
{
  public CraftEnderman(CraftServer server, EntityEnderman entity)
  {
    super(server, entity);
  }
  
  public MaterialData getCarriedMaterial() {
    return Material.getMaterial(getHandle().getCarriedId()).getNewData((byte)getHandle().getCarriedData());
  }
  
  public void setCarriedMaterial(MaterialData data) {
    getHandle().setCarriedId(data.getItemTypeId());
    getHandle().setCarriedData(data.getData());
  }
  
  public EntityEnderman getHandle()
  {
    return (EntityEnderman)this.entity;
  }
  
  public String toString()
  {
    return "CraftEnderman";
  }
  
  public EntityType getType() {
    return EntityType.ENDERMAN;
  }
}
