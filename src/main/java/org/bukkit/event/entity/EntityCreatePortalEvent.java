package org.bukkit.event.entity;

import java.util.List;
import org.bukkit.PortalType;
import org.bukkit.block.BlockState;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class EntityCreatePortalEvent
  extends EntityEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private final List<BlockState> blocks;
  private boolean cancelled = false;
  private PortalType type = PortalType.CUSTOM;
  
  public EntityCreatePortalEvent(LivingEntity what, List<BlockState> blocks, PortalType type) {
    super(what);
    
    this.blocks = blocks;
    this.type = type;
  }
  
  public LivingEntity getEntity()
  {
    return (LivingEntity)this.entity;
  }
  




  public List<BlockState> getBlocks()
  {
    return this.blocks;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  




  public PortalType getPortalType()
  {
    return this.type;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
