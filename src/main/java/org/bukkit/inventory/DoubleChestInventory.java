package org.bukkit.inventory;

import org.bukkit.block.DoubleChest;

public abstract interface DoubleChestInventory
  extends Inventory
{
  public abstract Inventory getLeftSide();
  
  public abstract Inventory getRightSide();
  
  public abstract DoubleChest getHolder();
}
