package org.bukkit.inventory;

import org.bukkit.block.BrewingStand;

public abstract interface BrewerInventory
  extends Inventory
{
  public abstract ItemStack getIngredient();
  
  public abstract void setIngredient(ItemStack paramItemStack);
  
  public abstract BrewingStand getHolder();
}
