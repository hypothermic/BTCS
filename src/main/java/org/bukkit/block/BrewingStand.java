package org.bukkit.block;

import org.bukkit.inventory.BrewerInventory;

public abstract interface BrewingStand
  extends BlockState, ContainerBlock
{
  public abstract int getBrewingTime();
  
  public abstract void setBrewingTime(int paramInt);
  
  public abstract BrewerInventory getInventory();
}
