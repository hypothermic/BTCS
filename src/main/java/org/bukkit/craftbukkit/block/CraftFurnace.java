package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityFurnace;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.FurnaceInventory;

public class CraftFurnace extends CraftBlockState implements org.bukkit.block.Furnace
{
  private final CraftWorld world;
  private final TileEntityFurnace furnace;
  
  public CraftFurnace(Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.furnace = ((TileEntityFurnace)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  public FurnaceInventory getInventory() {
    return new org.bukkit.craftbukkit.inventory.CraftInventoryFurnace(this.furnace);
  }
  
  public boolean update(boolean force)
  {
    boolean result = super.update(force);
    
    if (result) {
      this.furnace.update();
    }
    
    return result;
  }
  
  public short getBurnTime() {
    return (short)this.furnace.burnTime;
  }
  
  public void setBurnTime(short burnTime) {
    this.furnace.burnTime = burnTime;
  }
  
  public short getCookTime() {
    return (short)this.furnace.cookTime;
  }
  
  public void setCookTime(short cookTime) {
    this.furnace.cookTime = cookTime;
  }
}
