package org.bukkit.event.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;








public class BlockCanBuildEvent
  extends BlockEvent
{
  private static final HandlerList handlers = new HandlerList();
  protected boolean buildable;
  protected int material;
  
  public BlockCanBuildEvent(Block block, int id, boolean canBuild) {
    super(block);
    this.buildable = canBuild;
    this.material = id;
  }
  





  public boolean isBuildable()
  {
    return this.buildable;
  }
  




  public void setBuildable(boolean cancel)
  {
    this.buildable = cancel;
  }
  




  public Material getMaterial()
  {
    return Material.getMaterial(this.material);
  }
  




  public int getMaterialId()
  {
    return this.material;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}
