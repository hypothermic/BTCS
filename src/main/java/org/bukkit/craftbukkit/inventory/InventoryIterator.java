package org.bukkit.craftbukkit.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryIterator implements java.util.ListIterator<ItemStack>
{
  private final Inventory inventory;
  private int nextIndex;
  private Boolean lastDirection;
  
  InventoryIterator(Inventory craftInventory)
  {
    this.inventory = craftInventory;
    this.nextIndex = 0;
  }
  
  InventoryIterator(Inventory craftInventory, int index) {
    this.inventory = craftInventory;
    this.nextIndex = index;
  }
  
  public boolean hasNext() {
    return this.nextIndex < this.inventory.getSize();
  }
  
  public ItemStack next() {
    this.lastDirection = Boolean.valueOf(true);
    return this.inventory.getItem(this.nextIndex++);
  }
  
  public int nextIndex() {
    return this.nextIndex;
  }
  
  public boolean hasPrevious() {
    return this.nextIndex > 0;
  }
  
  public ItemStack previous() {
    this.lastDirection = Boolean.valueOf(false);
    return this.inventory.getItem(--this.nextIndex);
  }
  
  public int previousIndex() {
    return this.nextIndex - 1;
  }
  
  public void set(ItemStack item) {
    if (this.lastDirection == null) {
      throw new IllegalStateException("No current item!");
    }
    int i = this.lastDirection.booleanValue() ? this.nextIndex - 1 : this.nextIndex;
    this.inventory.setItem(i, item);
  }
  
  public void add(ItemStack item) {
    throw new UnsupportedOperationException("Can't change the size of an inventory!");
  }
  
  public void remove() {
    throw new UnsupportedOperationException("Can't change the size of an inventory!");
  }
}
