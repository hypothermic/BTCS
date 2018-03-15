package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;

public abstract interface Furnace
  extends BlockState, ContainerBlock
{
  public abstract short getBurnTime();
  
  public abstract void setBurnTime(short paramShort);
  
  public abstract short getCookTime();
  
  public abstract void setCookTime(short paramShort);
  
  public abstract FurnaceInventory getInventory();
}
