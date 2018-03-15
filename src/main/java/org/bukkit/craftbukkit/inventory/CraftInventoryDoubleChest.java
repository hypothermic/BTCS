package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.InventoryLargeChest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryDoubleChest extends CraftInventory implements org.bukkit.inventory.DoubleChestInventory
{
  private CraftInventory left;
  private CraftInventory right;
  
  public CraftInventoryDoubleChest(CraftInventory left, CraftInventory right)
  {
    super(new InventoryLargeChest("Large chest", left.getInventory(), right.getInventory()));
    this.left = left;
    this.right = right;
  }
  
  public CraftInventoryDoubleChest(InventoryLargeChest largeChest) {
    super(largeChest);
    if ((largeChest.left instanceof InventoryLargeChest)) {
      this.left = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.left);
    } else {
      this.left = new CraftInventory(largeChest.left);
    }
    if ((largeChest.right instanceof InventoryLargeChest)) {
      this.right = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.right);
    } else {
      this.right = new CraftInventory(largeChest.right);
    }
  }
  
  public Inventory getLeftSide() {
    return this.left;
  }
  
  public Inventory getRightSide() {
    return this.right;
  }
  
  public void setContents(ItemStack[] items)
  {
    if (getInventory().getContents().length < items.length) {
      throw new IllegalArgumentException("Invalid inventory size; expected " + getInventory().getContents().length + " or less");
    }
    ItemStack[] leftItems = new ItemStack[this.left.getSize()];ItemStack[] rightItems = new ItemStack[this.right.getSize()];
    System.arraycopy(items, 0, leftItems, 0, Math.min(this.left.getSize(), items.length));
    this.left.setContents(leftItems);
    if (items.length >= this.left.getSize()) {
      System.arraycopy(items, this.left.getSize(), rightItems, 0, Math.min(this.right.getSize(), items.length - this.left.getSize()));
      this.right.setContents(rightItems);
    }
  }
  
  public org.bukkit.block.DoubleChest getHolder()
  {
    return new org.bukkit.block.DoubleChest(this);
  }
}
