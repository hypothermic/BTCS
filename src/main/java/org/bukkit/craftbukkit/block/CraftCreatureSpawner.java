package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityMobSpawner;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.EntityType;

public class CraftCreatureSpawner extends CraftBlockState implements org.bukkit.block.CreatureSpawner
{
  private final CraftWorld world;
  private final TileEntityMobSpawner spawner;
  
  public CraftCreatureSpawner(Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.spawner = ((TileEntityMobSpawner)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  @Deprecated
  public CreatureType getCreatureType() {
    return CreatureType.fromName(this.spawner.mobName);
  }
  
  public EntityType getSpawnedType() {
    return EntityType.fromName(this.spawner.mobName);
  }
  
  @Deprecated
  public void setCreatureType(CreatureType creatureType) {
    this.spawner.mobName = creatureType.getName();
  }
  
  public void setSpawnedType(EntityType creatureType) {
    if ((!creatureType.isAlive()) || (!creatureType.isSpawnable())) {
      throw new IllegalArgumentException("Can't spawn non-living entities from mob spawners!");
    }
    
    this.spawner.mobName = creatureType.getName();
  }
  
  public String getCreatureTypeId() {
    return this.spawner.mobName;
  }
  
  @Deprecated
  public void setCreatureTypeId(String creatureName) {
    setCreatureTypeByName(creatureName);
  }
  
  public String getCreatureTypeName() {
    return this.spawner.mobName;
  }
  
  public void setCreatureTypeByName(String creatureType)
  {
    EntityType type = EntityType.fromName(creatureType);
    if (type == null) {
      return;
    }
    setSpawnedType(type);
  }
  
  public int getDelay() {
    return this.spawner.spawnDelay;
  }
  
  public void setDelay(int delay) {
    this.spawner.spawnDelay = delay;
  }
}
