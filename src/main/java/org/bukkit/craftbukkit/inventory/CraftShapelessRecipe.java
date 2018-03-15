package org.bukkit.craftbukkit.inventory;

import java.util.List;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.ShapelessRecipes;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftShapelessRecipe
  extends ShapelessRecipe
  implements CraftRecipe
{
  private ShapelessRecipes recipe;
  
  public CraftShapelessRecipe(org.bukkit.inventory.ItemStack result)
  {
    super(result);
  }
  
  public CraftShapelessRecipe(org.bukkit.inventory.ItemStack result, ShapelessRecipes recipe) {
    this(result);
    this.recipe = recipe;
  }
  
  public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
    if ((recipe instanceof CraftShapelessRecipe)) {
      return (CraftShapelessRecipe)recipe;
    }
    CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getResult());
    for (org.bukkit.inventory.ItemStack ingred : recipe.getIngredientList()) {
      ret.addIngredient(ingred.getType(), ingred.getDurability());
    }
    return ret;
  }
  
  public void addToCraftingManager() {
    List<org.bukkit.inventory.ItemStack> ingred = getIngredientList();
    Object[] data = new Object[ingred.size()];
    int i = 0;
    for (org.bukkit.inventory.ItemStack mdata : ingred) {
      int id = mdata.getTypeId();
      short dmg = mdata.getDurability();
      data[i] = new net.minecraft.server.ItemStack(id, 1, dmg);
      i++;
    }
    CraftingManager.getInstance().registerShapelessRecipe(CraftItemStack.createNMSItemStack(getResult()), data);
  }
}
