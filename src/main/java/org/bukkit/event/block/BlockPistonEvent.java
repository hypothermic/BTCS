package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public abstract class BlockPistonEvent extends BlockEvent implements org.bukkit.event.Cancellable
{
  private boolean cancelled;
  private final BlockFace direction;
  
  public BlockPistonEvent(Block block, BlockFace direction)
  {
    super(block);
    this.direction = direction;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }
  




  public boolean isSticky()
  {
    return this.block.getType() == org.bukkit.Material.PISTON_STICKY_BASE;
  }
  







  public BlockFace getDirection()
  {
    return this.direction;
  }
}
