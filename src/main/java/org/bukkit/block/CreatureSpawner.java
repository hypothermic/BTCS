package org.bukkit.block;

import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;

public abstract interface CreatureSpawner
  extends BlockState
{
  @Deprecated
  public abstract CreatureType getCreatureType();
  
  public abstract EntityType getSpawnedType();
  
  public abstract void setSpawnedType(EntityType paramEntityType);
  
  @Deprecated
  public abstract void setCreatureType(CreatureType paramCreatureType);
  
  @Deprecated
  public abstract String getCreatureTypeId();
  
  public abstract void setCreatureTypeByName(String paramString);
  
  public abstract String getCreatureTypeName();
  
  @Deprecated
  public abstract void setCreatureTypeId(String paramString);
  
  public abstract int getDelay();
  
  public abstract void setDelay(int paramInt);
}
