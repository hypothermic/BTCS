package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryCrafting;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.util.Java15Compat;

public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory
{
  private IInventory resultInventory;
  
  public CraftInventoryCrafting(InventoryCrafting inventory, IInventory resultInventory)
  {
    super(inventory);
    this.resultInventory = resultInventory;
  }
  
  public IInventory getResultInventory() {
    return this.resultInventory;
  }
  
  public IInventory getMatrixInventory() {
    return this.inventory;
  }
  
  public int getSize()
  {
    return getResultInventory().getSize() + getMatrixInventory().getSize();
  }
  
  public void setContents(org.bukkit.inventory.ItemStack[] items)
  {
    int resultLen = getResultInventory().getContents().length;
    int len = getMatrixInventory().getContents().length + resultLen;
    if (len > items.length) {
      throw new IllegalArgumentException("Invalid inventory size; expected " + len + " or less");
    }
    setContents(items[0], (org.bukkit.inventory.ItemStack[])Java15Compat.Arrays_copyOfRange(items, 1, items.length));
  }
  
  public org.bukkit.inventory.ItemStack[] getContents()
  {
    org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[getSize()];
    net.minecraft.server.ItemStack[] mcResultItems = getResultInventory().getContents();
    
    int i = 0;
    for (i = 0; i < mcResultItems.length; i++) {
      items[i] = new CraftItemStack(mcResultItems[i]);
    }
    
    net.minecraft.server.ItemStack[] mcItems = getMatrixInventory().getContents();
    
    for (int j = 0; j < mcItems.length; j++) {
      items[(i + j)] = new CraftItemStack(mcItems[j]);
    }
    
    return items;
  }
  
  public void setContents(org.bukkit.inventory.ItemStack result, org.bukkit.inventory.ItemStack[] contents) {
    setResult(result);
    setMatrix(contents);
  }
  
  public CraftItemStack getItem(int index)
  {
    if (index < getResultInventory().getSize()) {
      net.minecraft.server.ItemStack item = getResultInventory().getItem(index);
      return item == null ? null : new CraftItemStack(item);
    }
    net.minecraft.server.ItemStack item = getMatrixInventory().getItem(index - getResultInventory().getSize());
    return item == null ? null : new CraftItemStack(item);
  }
  

  public void setItem(int index, org.bukkit.inventory.ItemStack item)
  {
    if (index < getResultInventory().getSize()) {
      getResultInventory().setItem(index, item == null ? null : CraftItemStack.createNMSItemStack(item));
    } else {
      getMatrixInventory().setItem(index - getResultInventory().getSize(), item == null ? null : CraftItemStack.createNMSItemStack(item));
    }
  }
  
  public org.bukkit.inventory.ItemStack[] getMatrix() {
    org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[getSize()];
    net.minecraft.server.ItemStack[] matrix = getMatrixInventory().getContents();
    
    for (int i = 0; i < matrix.length; i++) {
      items[i] = new CraftItemStack(matrix[i]);
    }
    
    return items;
  }
  
  public org.bukkit.inventory.ItemStack getResult() {
    net.minecraft.server.ItemStack item = getResultInventory().getItem(0);
    if (item != null) return new CraftItemStack(item);
    return null;
  }
  
  public void setMatrix(org.bukkit.inventory.ItemStack[] contents) {
    if (getMatrixInventory().getContents().length > contents.length) {
      throw new IllegalArgumentException("Invalid inventory size; expected " + getMatrixInventory().getContents().length + " or less");
    }
    
    net.minecraft.server.ItemStack[] mcItems = getMatrixInventory().getContents();
    
    for (int i = 0; i < mcItems.length; i++) {
      if (i < contents.length) {
        org.bukkit.inventory.ItemStack item = contents[i];
        if ((item == null) || (item.getTypeId() <= 0)) {
          mcItems[i] = null;
        } else {
          mcItems[i] = CraftItemStack.createNMSItemStack(item);
        }
      } else {
        mcItems[i] = null;
      }
    }
  }
  
  public void setResult(org.bukkit.inventory.ItemStack item) {
    net.minecraft.server.ItemStack[] contents = getResultInventory().getContents();
    if ((item == null) || (item.getTypeId() <= 0)) {
      contents[0] = null;
    } else {
      contents[0] = CraftItemStack.createNMSItemStack(item);
    }
  }
  
  public Recipe getRecipe() {
    CraftingRecipe recipe = ((InventoryCrafting)getInventory()).currentRecipe;
    return recipe == null ? null : recipe.toBukkitRecipe();
  }
}
