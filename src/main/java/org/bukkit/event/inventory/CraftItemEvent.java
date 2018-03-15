package org.bukkit.event.inventory;

import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Recipe;

public class CraftItemEvent extends InventoryClickEvent
{
  private Recipe recipe;
  
  public CraftItemEvent(Recipe recipe, org.bukkit.inventory.InventoryView what, InventoryType.SlotType type, int slot, boolean right, boolean shift)
  {
    super(what, type, slot, right, shift);
    this.recipe = recipe;
  }
  


  public Recipe getRecipe()
  {
    return this.recipe;
  }
  
  public CraftingInventory getInventory()
  {
    return (CraftingInventory)super.getInventory();
  }
}
