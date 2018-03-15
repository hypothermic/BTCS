package org.bukkit.inventory;

public abstract interface CraftingInventory
  extends Inventory
{
  public abstract ItemStack getResult();
  
  public abstract ItemStack[] getMatrix();
  
  public abstract void setResult(ItemStack paramItemStack);
  
  public abstract void setMatrix(ItemStack[] paramArrayOfItemStack);
  
  public abstract Recipe getRecipe();
}
