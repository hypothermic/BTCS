package org.bukkit.inventory;

public abstract interface EnchantingInventory
  extends Inventory
{
  public abstract void setItem(ItemStack paramItemStack);
  
  public abstract ItemStack getItem();
}
