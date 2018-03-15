package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.IInventory;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryBrewer extends CraftInventory implements org.bukkit.inventory.BrewerInventory
{
  public CraftInventoryBrewer(IInventory inventory)
  {
    super(inventory);
  }
  
  public ItemStack getIngredient() {
    return getItem(3);
  }
  
  public void setIngredient(ItemStack ingredient) {
    setItem(3, ingredient);
  }
  
  public BrewingStand getHolder()
  {
    return (BrewingStand)this.inventory.getOwner();
  }
}
