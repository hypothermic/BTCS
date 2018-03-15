package org.bukkit.event.block;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.HandlerList;

public class BlockPistonExtendEvent extends BlockPistonEvent
{
  private static final HandlerList handlers = new HandlerList();
  private final int length;
  private List<Block> blocks;
  
  public BlockPistonExtendEvent(Block block, int length, BlockFace direction) {
    super(block, direction);
    
    this.length = length;
  }
  




  public int getLength()
  {
    return this.length;
  }
  




  public List<Block> getBlocks()
  {
    if (this.blocks == null) {
      ArrayList<Block> tmp = new ArrayList();
      for (int i = 0; i < getLength(); i++) {
        tmp.add(this.block.getRelative(getDirection(), i + 1));
      }
      this.blocks = Collections.unmodifiableList(tmp);
    }
    return this.blocks;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
