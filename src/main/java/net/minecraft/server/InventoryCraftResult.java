package net.minecraft.server;

import java.util.ArrayList;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCraftResult implements IInventory
{
  private ItemStack[] items = new ItemStack[1];
  

  private int maxStack = 64;
  
  public ItemStack[] getContents() {
    return this.items;
  }
  
  public InventoryHolder getOwner() { return null; }
  
  public void onOpen(CraftHumanEntity who) {}
  
  public void onClose(CraftHumanEntity who) {}
  
  public java.util.List<HumanEntity> getViewers() {
    return new ArrayList();
  }
  
  public void setMaxStackSize(int size) {
    this.maxStack = size;
  }
  


  public int getSize()
  {
    return 1;
  }
  
  public ItemStack getItem(int i) {
    return this.items[i];
  }
  
  public String getName() {
    return "Result";
  }
  
  public ItemStack splitStack(int i, int j) {
    if (this.items[i] != null) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public ItemStack splitWithoutUpdate(int i)
  {
    if (this.items[i] != null) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public void setItem(int i, ItemStack itemstack)
  {
    this.items[i] = itemstack;
  }
  
  public int getMaxStackSize() {
    return this.maxStack;
  }
  
  public void update() {}
  
  public boolean a(EntityHuman entityhuman) {
    return true;
  }
  
  public void f() {}
  
  public void g() {}
}
