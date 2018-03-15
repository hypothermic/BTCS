package org.bukkit.event.entity;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;



public class EntityChangeBlockEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final Block block;
  private boolean cancel;
  private final Material to;
  
  public EntityChangeBlockEvent(LivingEntity what, Block block, Material to) {
    super(what);
    this.block = block;
    this.cancel = false;
    this.to = to;
  }
  
  public LivingEntity getEntity()
  {
    return (LivingEntity)this.entity;
  }
  




  public Block getBlock()
  {
    return this.block;
  }
  
  public boolean isCancelled() {
    return this.cancel;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  




  public Material getTo()
  {
    return this.to;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
