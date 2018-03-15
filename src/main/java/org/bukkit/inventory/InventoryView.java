package org.bukkit.inventory;

import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;




public abstract class InventoryView
{
  public static final int OUTSIDE = -999;
  public abstract Inventory getTopInventory();
  
  public abstract Inventory getBottomInventory();
  
  public abstract HumanEntity getPlayer();
  
  public abstract InventoryType getType();
  
  public static enum Property
  {
    BREW_TIME(0, InventoryType.BREWING), 
    


    COOK_TIME(0, InventoryType.FURNACE), 
    


    BURN_TIME(1, InventoryType.FURNACE), 
    


    TICKS_FOR_CURRENT_FUEL(2, InventoryType.FURNACE), 
    


    ENCHANT_BUTTON1(0, InventoryType.ENCHANTING), 
    


    ENCHANT_BUTTON2(1, InventoryType.ENCHANTING), 
    


    ENCHANT_BUTTON3(2, InventoryType.ENCHANTING);
    
    int id;
    
    private Property(int id, InventoryType appliesTo) { this.id = id;
      this.style = appliesTo;
    }
    
    public InventoryType getType() {
      return this.style;
    }
    
    public int getId() {
      return this.id;
    }
    















    InventoryType style;
  }
  















  public void setItem(int slot, ItemStack item)
  {
    if (slot != 64537) {
      if (slot < getTopInventory().getSize()) {
        getTopInventory().setItem(convertSlot(slot), item);
      } else {
        getBottomInventory().setItem(convertSlot(slot), item);
      }
    } else {
      getPlayer().getWorld().dropItemNaturally(getPlayer().getLocation(), item);
    }
  }
  




  public ItemStack getItem(int slot)
  {
    if (slot == 64537) {
      return null;
    }
    if (slot < getTopInventory().getSize()) {
      return getTopInventory().getItem(convertSlot(slot));
    }
    return getBottomInventory().getItem(convertSlot(slot));
  }
  




  public final void setCursor(ItemStack item)
  {
    getPlayer().setItemOnCursor(item);
  }
  



  public final ItemStack getCursor()
  {
    return getPlayer().getItemOnCursor();
  }
  








  public final int convertSlot(int rawSlot)
  {
    int numInTop = getTopInventory().getSize();
    if (rawSlot < numInTop) {
      return rawSlot;
    }
    int slot = rawSlot - numInTop;
    if (getType() == InventoryType.CRAFTING) {
      if (slot < 4) return 39 - slot;
      slot -= 4;
    }
    if (slot >= 27) slot -= 27; else
      slot += 9;
    return slot;
  }
  


  public final void close()
  {
    getPlayer().closeInventory();
  }
  





  public final int countSlots()
  {
    return getTopInventory().getSize() + getBottomInventory().getSize();
  }
  
  public final boolean setProperty(Property prop, int value) {
    return getPlayer().setWindowProperty(prop, value);
  }
  



  public final String getTitle()
  {
    return getTopInventory().getTitle();
  }
}
