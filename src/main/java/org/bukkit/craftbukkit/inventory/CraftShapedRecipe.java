package org.bukkit.craftbukkit.inventory;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.CraftingManager;
import net.minecraft.server.ShapedRecipes;
import org.bukkit.inventory.ShapedRecipe;

public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe
{
  private ShapedRecipes recipe;
  
  public CraftShapedRecipe(org.bukkit.inventory.ItemStack result)
  {
    super(result);
  }
  
  public CraftShapedRecipe(org.bukkit.inventory.ItemStack result, ShapedRecipes recipe) {
    this(result);
    this.recipe = recipe;
  }
  
  public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
    if ((recipe instanceof CraftShapedRecipe)) {
      return (CraftShapedRecipe)recipe;
    }
    CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());
    String[] shape = recipe.getShape();
    ret.shape(shape);
    Map<Character, org.bukkit.inventory.ItemStack> ingredientMap = recipe.getIngredientMap();
    for (Iterator i$ = ingredientMap.keySet().iterator(); i$.hasNext();) { char c = ((Character)i$.next()).charValue();
      org.bukkit.inventory.ItemStack stack = (org.bukkit.inventory.ItemStack)ingredientMap.get(Character.valueOf(c));
      if (stack != null) {
        ret.setIngredient(c, stack.getType(), stack.getDurability());
      }
    }
    return ret;
  }
  
  public void addToCraftingManager()
  {
    String[] shape = getShape();
    Map<Character, org.bukkit.inventory.ItemStack> ingred = getIngredientMap();
    int datalen = shape.length;
    datalen += ingred.size() * 2;
    int i = 0;
    Object[] data = new Object[datalen];
    for (; i < shape.length; i++) {
      data[i] = shape[i];
    }
    for (Iterator i$ = ingred.keySet().iterator(); i$.hasNext();) { char c = ((Character)i$.next()).charValue();
      org.bukkit.inventory.ItemStack mdata = (org.bukkit.inventory.ItemStack)ingred.get(Character.valueOf(c));
      if (mdata != null) {
        data[i] = Character.valueOf(c);
        i++;
        int id = mdata.getTypeId();
        short dmg = mdata.getDurability();
        data[i] = new net.minecraft.server.ItemStack(id, 1, dmg);
        i++;
      } }
    CraftingManager.getInstance().registerShapedRecipe(CraftItemStack.createNMSItemStack(getResult()), data);
  }
}
