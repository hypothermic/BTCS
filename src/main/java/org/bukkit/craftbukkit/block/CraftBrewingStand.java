package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityBrewingStand;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventoryBrewer;

public class CraftBrewingStand extends CraftBlockState implements org.bukkit.block.BrewingStand
{
  private final CraftWorld world;
  private final TileEntityBrewingStand brewingStand;
  
  public CraftBrewingStand(Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.brewingStand = ((TileEntityBrewingStand)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  public org.bukkit.inventory.BrewerInventory getInventory() {
    return new CraftInventoryBrewer(this.brewingStand);
  }
  
  public boolean update(boolean force)
  {
    boolean result = super.update(force);
    
    if (result) {
      this.brewingStand.update();
    }
    
    return result;
  }
  
  public int getBrewingTime() {
    return this.brewingStand.brewTime;
  }
  
  public void setBrewingTime(int brewTime) {
    this.brewingStand.brewTime = brewTime;
  }
}
