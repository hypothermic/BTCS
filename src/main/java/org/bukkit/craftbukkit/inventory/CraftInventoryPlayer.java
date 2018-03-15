package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.IInventory;
import org.bukkit.entity.HumanEntity;

public class CraftInventoryPlayer extends CraftInventory implements org.bukkit.inventory.PlayerInventory
{
  public CraftInventoryPlayer(net.minecraft.server.PlayerInventory inventory)
  {
    super(inventory);
  }
  
  public net.minecraft.server.PlayerInventory getInventory()
  {
    return (net.minecraft.server.PlayerInventory)this.inventory;
  }
  
  public int getSize()
  {
    return super.getSize() - 4;
  }
  
  public org.bukkit.inventory.ItemStack getItemInHand() {
    return new CraftItemStack(getInventory().getItemInHand());
  }
  
  public void setItemInHand(org.bukkit.inventory.ItemStack stack) {
    setItem(getHeldItemSlot(), stack);
  }
  
  public int getHeldItemSlot() {
    return getInventory().itemInHandIndex;
  }
  
  public org.bukkit.inventory.ItemStack getHelmet() {
    return getItem(getSize() + 3);
  }
  
  public org.bukkit.inventory.ItemStack getChestplate() {
    return getItem(getSize() + 2);
  }
  
  public org.bukkit.inventory.ItemStack getLeggings() {
    return getItem(getSize() + 1);
  }
  
  public org.bukkit.inventory.ItemStack getBoots() {
    return getItem(getSize() + 0);
  }
  
  public void setHelmet(org.bukkit.inventory.ItemStack helmet) {
    setItem(getSize() + 3, helmet);
  }
  
  public void setChestplate(org.bukkit.inventory.ItemStack chestplate) {
    setItem(getSize() + 2, chestplate);
  }
  
  public void setLeggings(org.bukkit.inventory.ItemStack leggings) {
    setItem(getSize() + 1, leggings);
  }
  
  public void setBoots(org.bukkit.inventory.ItemStack boots) {
    setItem(getSize() + 0, boots);
  }
  
  public org.bukkit.inventory.ItemStack[] getArmorContents() {
    net.minecraft.server.ItemStack[] mcItems = getInventory().getArmorContents();
    org.bukkit.inventory.ItemStack[] ret = new org.bukkit.inventory.ItemStack[mcItems.length];
    
    for (int i = 0; i < mcItems.length; i++) {
      ret[i] = new CraftItemStack(mcItems[i]);
    }
    return ret;
  }
  
  public void setArmorContents(org.bukkit.inventory.ItemStack[] items) {
    int cnt = getSize();
    
    if (items == null) {
      items = new org.bukkit.inventory.ItemStack[4];
    }
    for (org.bukkit.inventory.ItemStack item : items) {
      if ((item == null) || (item.getTypeId() == 0)) {
        clear(cnt++);
      } else {
        setItem(cnt++, item);
      }
    }
  }
  
  public HumanEntity getHolder()
  {
    return (HumanEntity)this.inventory.getOwner();
  }
}
