package net.minecraft.server;

import java.util.Comparator;

class RecipeSorter implements Comparator
{
  final CraftingManager a;
  
  RecipeSorter(CraftingManager craftingmanager) {
    this.a = craftingmanager;
  }
  
  public int compare(Object craftingrecipe, Object craftingrecipe1)
  {
    if ((!(craftingrecipe instanceof CraftingRecipe)) || (!(craftingrecipe1 instanceof CraftingRecipe))) {
      throw new IllegalArgumentException();
    }
    return a((CraftingRecipe)craftingrecipe, (CraftingRecipe)craftingrecipe);
  }
  
  public int a(CraftingRecipe craftingrecipe, CraftingRecipe craftingrecipe1)
  {
    return craftingrecipe1.a() > craftingrecipe.a() ? 1 : craftingrecipe1.a() < craftingrecipe.a() ? -1 : ((craftingrecipe1 instanceof ShapelessRecipes)) && ((craftingrecipe instanceof ShapedRecipes)) ? -1 : ((craftingrecipe instanceof ShapelessRecipes)) && ((craftingrecipe1 instanceof ShapedRecipes)) ? 1 : 0;
  }
}
