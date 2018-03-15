package org.bukkit.block;

import org.bukkit.inventory.Inventory;

public abstract interface Chest
  extends BlockState, ContainerBlock
{
  public abstract Inventory getBlockInventory();
}
