package org.bukkit.craftbukkit.block;

import net.minecraft.server.TileEntityChest;
import net.minecraft.server.WorldServer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryDoubleChest;
import org.bukkit.inventory.Inventory;

public class CraftChest extends CraftBlockState implements org.bukkit.block.Chest
{
  private final CraftWorld world;
  private final TileEntityChest chest;
  
  public CraftChest(Block block)
  {
    super(block);
    
    this.world = ((CraftWorld)block.getWorld());
    this.chest = ((TileEntityChest)this.world.getTileEntityAt(getX(), getY(), getZ()));
  }
  
  public Inventory getBlockInventory() {
    return new CraftInventory(this.chest);
  }
  
  public Inventory getInventory() {
    int x = getX();
    int y = getY();
    int z = getZ();
    
    CraftInventory inventory = new CraftInventory(this.chest);
    if (this.world.getBlockTypeIdAt(x - 1, y, z) == Material.CHEST.getId()) {
      CraftInventory left = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(x - 1, y, z));
      inventory = new CraftInventoryDoubleChest(left, inventory);
    }
    if (this.world.getBlockTypeIdAt(x + 1, y, z) == Material.CHEST.getId()) {
      CraftInventory right = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(x + 1, y, z));
      inventory = new CraftInventoryDoubleChest(inventory, right);
    }
    if (this.world.getBlockTypeIdAt(x, y, z - 1) == Material.CHEST.getId()) {
      CraftInventory left = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(x, y, z - 1));
      inventory = new CraftInventoryDoubleChest(left, inventory);
    }
    if (this.world.getBlockTypeIdAt(x, y, z + 1) == Material.CHEST.getId()) {
      CraftInventory right = new CraftInventory((TileEntityChest)this.world.getHandle().getTileEntity(x, y, z + 1));
      inventory = new CraftInventoryDoubleChest(inventory, right);
    }
    return inventory;
  }
  
  public boolean update(boolean force)
  {
    boolean result = super.update(force);
    
    if (result) {
      this.chest.update();
    }
    
    return result;
  }
}
