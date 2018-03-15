package org.bukkit.craftbukkit.inventory;

import org.bukkit.inventory.Recipe;

public abstract interface CraftRecipe
  extends Recipe
{
  public abstract void addToCraftingManager();
}
