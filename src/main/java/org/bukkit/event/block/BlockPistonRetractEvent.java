package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.HandlerList;

public class BlockPistonRetractEvent extends BlockPistonEvent
{
  private static final HandlerList handlers = new HandlerList();
  
  public BlockPistonRetractEvent(Block block, BlockFace direction) { super(block, direction); }
  






  public org.bukkit.Location getRetractLocation()
  {
    return getBlock().getRelative(getDirection(), 2).getLocation();
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
