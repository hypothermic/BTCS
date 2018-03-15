package org.bukkit.event.painting;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class PaintingPlaceEvent
  extends PaintingEvent
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean cancelled;
  private final Player player;
  private final Block block;
  private final BlockFace blockFace;
  
  public PaintingPlaceEvent(Painting painting, Player player, Block block, BlockFace blockFace) {
    super(painting);
    this.player = player;
    this.block = block;
    this.blockFace = blockFace;
  }
  




  public Player getPlayer()
  {
    return this.player;
  }
  




  public Block getBlock()
  {
    return this.block;
  }
  




  public BlockFace getBlockFace()
  {
    return this.blockFace;
  }
  
  public boolean isCancelled() {
    return this.cancelled;
  }
  
  public void setCancelled(boolean cancel) {
    this.cancelled = cancel;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
