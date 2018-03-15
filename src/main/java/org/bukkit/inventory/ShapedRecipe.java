package org.bukkit.inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;



public class ShapedRecipe
  implements Recipe
{
  private ItemStack output;
  private String[] rows;
  private Map<Character, ItemStack> ingredients = new HashMap();
  









  public ShapedRecipe(ItemStack result)
  {
    this.output = new ItemStack(result);
  }
  






  public ShapedRecipe shape(String... shape)
  {
    Validate.notNull(shape, "Must provide a shape");
    Validate.isTrue((shape.length > 0) && (shape.length < 4), "Crafting recipes should be 1, 2, 3 rows, not ", shape.length);
    
    for (String row : shape) {
      Validate.notNull(row, "Shape cannot have null rows");
      Validate.isTrue((row.length() > 0) && (row.length() < 4), "Crafting rows should be 1, 2, or 3 characters, not ", row.length());
    }
    this.rows = new String[shape.length];
    for (int i = 0; i < shape.length; i++) {
      this.rows[i] = shape[i];
    }
    

    HashMap<Character, ItemStack> newIngredients = new HashMap();
    for (String row : shape) {
      char[] arr$ = row.toCharArray();int len$ = arr$.length; for (int i$ = 0; i$ < len$; i$++) { Character c = Character.valueOf(arr$[i$]);
        newIngredients.put(c, this.ingredients.get(c));
      }
    }
    this.ingredients = newIngredients;
    
    return this;
  }
  






  public ShapedRecipe setIngredient(char key, MaterialData ingredient)
  {
    return setIngredient(key, ingredient.getItemType(), ingredient.getData());
  }
  






  public ShapedRecipe setIngredient(char key, Material ingredient)
  {
    return setIngredient(key, ingredient, 0);
  }
  







  public ShapedRecipe setIngredient(char key, Material ingredient, int raw)
  {
    Validate.isTrue(this.ingredients.containsKey(Character.valueOf(key)), "Symbol does not appear in the shape:", key);
    this.ingredients.put(Character.valueOf(key), new ItemStack(ingredient, 1, (short)raw));
    return this;
  }
  




  public Map<Character, ItemStack> getIngredientMap()
  {
    HashMap<Character, ItemStack> result = new HashMap();
    for (Map.Entry<Character, ItemStack> ingredient : this.ingredients.entrySet()) {
      if (ingredient.getValue() == null) {
        result.put(ingredient.getKey(), null);
      } else {
        result.put(ingredient.getKey(), ((ItemStack)ingredient.getValue()).clone());
      }
    }
    return result;
  }
  




  public String[] getShape()
  {
    return (String[])this.rows.clone();
  }
  




  public ItemStack getResult()
  {
    return this.output.clone();
  }
}
