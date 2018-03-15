package org.bukkit.block;

import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class DoubleChest implements InventoryHolder
{
  private DoubleChestInventory inventory;
  
  public DoubleChest(DoubleChestInventory chest)
  {
    this.inventory = chest;
  }
  
  public Inventory getInventory() {
    return this.inventory;
  }
  
  public InventoryHolder getLeftSide() {
    return this.inventory.getLeftSide().getHolder();
  }
  
  public InventoryHolder getRightSide() {
    return this.inventory.getRightSide().getHolder();
  }
  
  public org.bukkit.Location getLocation() {
    return new org.bukkit.Location(getWorld(), getX(), getY(), getZ());
  }
  
  public org.bukkit.World getWorld() {
    return ((Chest)getLeftSide()).getWorld();
  }
  
  public double getX() {
    return 0.5D * (((Chest)getLeftSide()).getX() + ((Chest)getRightSide()).getX());
  }
  
  public double getY() {
    return 0.5D * (((Chest)getLeftSide()).getY() + ((Chest)getRightSide()).getY());
  }
  
  public double getZ() {
    return 0.5D * (((Chest)getLeftSide()).getZ() + ((Chest)getRightSide()).getZ());
  }
}
