package org.bukkit.craftbukkit.inventory;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.FurnaceRecipes;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class RecipeIterator implements Iterator<Recipe>
{
  private Iterator<CraftingRecipe> recipes;
  private Iterator<Integer> smelting;
  private Iterator<?> removeFrom = null;
  
  public RecipeIterator() {
    this.recipes = CraftingManager.getInstance().getRecipies().iterator();
    this.smelting = FurnaceRecipes.getInstance().getRecipies().keySet().iterator();
  }
  
  public boolean hasNext() {
    if (this.recipes.hasNext()) {
      return true;
    }
    return this.smelting.hasNext();
  }
  
  public Recipe next()
  {
    if (this.recipes.hasNext()) {
      this.removeFrom = this.recipes;
      return ((CraftingRecipe)this.recipes.next()).toBukkitRecipe();
    }
    this.removeFrom = this.smelting;
    int id = ((Integer)this.smelting.next()).intValue();
    CraftItemStack stack = new CraftItemStack(FurnaceRecipes.getInstance().getResult(id));
    CraftFurnaceRecipe recipe = new CraftFurnaceRecipe(stack, new ItemStack(id, 1, (short)-1));
    return recipe;
  }
  
  public void remove()
  {
    if (this.removeFrom == null) {
      throw new IllegalStateException();
    }
    this.removeFrom.remove();
  }
}
