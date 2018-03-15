package org.bukkit.event.entity;

import org.bukkit.block.Block;

public class EntityCombustByBlockEvent extends EntityCombustEvent
{
  private final Block combuster;
  
  public EntityCombustByBlockEvent(Block combuster, org.bukkit.entity.Entity combustee, int duration) {
    super(combustee, duration);
    this.combuster = combuster;
  }
  






  public Block getCombuster()
  {
    return this.combuster;
  }
}
