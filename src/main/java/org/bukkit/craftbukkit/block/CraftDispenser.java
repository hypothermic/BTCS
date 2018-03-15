package org.bukkit.craftbukkit.block;

import java.util.Random;
import net.minecraft.server.BlockDispenser;
import net.minecraft.server.TileEntityDispenser;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.inventory.Inventory;

public class CraftDispenser extends CraftBlockState implements Dispenser
{
  private final CraftWorld world;
  private final TileEntityDispenser dispenser;
  
  public CraftDispenser(org.bukkit.block.Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.dispenser = ((TileEntityDispenser)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  public Inventory getInventory() {
    return new org.bukkit.craftbukkit.inventory.CraftInventory(this.dispenser);
  }
  
  public boolean dispense() {
    org.bukkit.block.Block block = getBlock();
    
    synchronized (block) {
      if (block.getType() == Material.DISPENSER) {
        BlockDispenser dispense = (BlockDispenser)net.minecraft.server.Block.DISPENSER;
        
        dispense.dispense(this.world.getHandle(), getX(), getY(), getZ(), new Random());
        return true;
      }
      return false;
    }
  }
  

  public boolean update(boolean force)
  {
    boolean result = super.update(force);
    
    if (result) {
      this.dispenser.update();
    }
    
    return result;
  }
}
