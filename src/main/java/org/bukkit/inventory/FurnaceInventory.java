package org.bukkit.inventory;

import org.bukkit.block.Furnace;

public abstract interface FurnaceInventory
  extends Inventory
{
  public abstract ItemStack getResult();
  
  public abstract ItemStack getFuel();
  
  public abstract ItemStack getSmelting();
  
  public abstract void setFuel(ItemStack paramItemStack);
  
  public abstract void setResult(ItemStack paramItemStack);
  
  public abstract void setSmelting(ItemStack paramItemStack);
  
  public abstract Furnace getHolder();
}
