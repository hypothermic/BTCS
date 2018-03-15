package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;



public class CreatureSpawnEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean canceled;
  private final SpawnReason spawnReason;
  
  public CreatureSpawnEvent(LivingEntity spawnee, SpawnReason spawnReason) {
    super(spawnee);
    this.spawnReason = spawnReason;
  }
  
  @Deprecated
  public CreatureSpawnEvent(Entity spawnee, CreatureType type, Location loc, SpawnReason reason) {
    super(spawnee);
    this.spawnReason = reason;
  }
  
  public boolean isCancelled() {
    return this.canceled;
  }
  
  public void setCancelled(boolean cancel) {
    this.canceled = cancel;
  }
  
  public LivingEntity getEntity()
  {
    return (LivingEntity)this.entity;
  }
  




  public Location getLocation()
  {
    return getEntity().getLocation();
  }
  





  @Deprecated
  public CreatureType getCreatureType()
  {
    return CreatureType.fromEntityType(getEntityType());
  }
  




  public SpawnReason getSpawnReason()
  {
    return this.spawnReason;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
  






  public static enum SpawnReason
  {
    NATURAL, 
    


    JOCKEY, 
    


    CHUNK_GEN, 
    


    SPAWNER, 
    


    EGG, 
    


    SPAWNER_EGG, 
    


    LIGHTNING, 
    




    BED, 
    



    BUILD_SNOWMAN, 
    


    BUILD_IRONGOLEM, 
    


    VILLAGE_DEFENSE, 
    


    VILLAGE_INVASION, 
    


    BREEDING, 
    


    SLIME_SPLIT, 
    


    CUSTOM, 
    


    DEFAULT;
    
    private SpawnReason() {}
  }
}
