package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.ContainerEnchantTableInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryEnchanting extends CraftInventory implements org.bukkit.inventory.EnchantingInventory
{
  public CraftInventoryEnchanting(ContainerEnchantTableInventory inventory)
  {
    super(inventory);
  }
  
  public void setItem(ItemStack item) {
    setItem(0, item);
  }
  
  public ItemStack getItem() {
    return getItem(0);
  }
  
  public ContainerEnchantTableInventory getInventory()
  {
    return (ContainerEnchantTableInventory)this.inventory;
  }
}
