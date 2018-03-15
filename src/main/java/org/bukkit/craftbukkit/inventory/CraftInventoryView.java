package org.bukkit.craftbukkit.inventory;

import net.minecraft.server.Container;
import net.minecraft.server.Slot;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public class CraftInventoryView extends InventoryView
{
  private Container container;
  private CraftHumanEntity player;
  private CraftInventory viewing;
  
  public CraftInventoryView(HumanEntity player, Inventory viewing, Container container)
  {
    this.player = ((CraftHumanEntity)player);
    this.viewing = ((CraftInventory)viewing);
    this.container = container;
  }
  
  public Inventory getTopInventory()
  {
    return this.viewing;
  }
  
  public Inventory getBottomInventory()
  {
    return this.player.getInventory();
  }
  
  public HumanEntity getPlayer()
  {
    return this.player;
  }
  
  public InventoryType getType()
  {
    InventoryType type = this.viewing.getType();
    if ((type == InventoryType.CRAFTING) && (this.player.getGameMode() == GameMode.CREATIVE)) {
      return InventoryType.CREATIVE;
    }
    return type;
  }
  
  public void setItem(int slot, org.bukkit.inventory.ItemStack item)
  {
    net.minecraft.server.ItemStack stack = CraftItemStack.createNMSItemStack(item);
    if (slot != 64537) {
      this.container.getSlot(slot).set(stack);
    } else {
      this.player.getHandle().drop(stack);
    }
  }
  
  public org.bukkit.inventory.ItemStack getItem(int slot)
  {
    if (slot == 64537) {
      return null;
    }
    return new CraftItemStack(this.container.getSlot(slot).getItem());
  }
  
  public boolean isInTop(int rawSlot) {
    return rawSlot < this.viewing.getSize();
  }
  
  public Container getHandle() {
    return this.container;
  }
  
  public static InventoryType.SlotType getSlotType(InventoryView inventory, int slot) {
    InventoryType.SlotType type = InventoryType.SlotType.CONTAINER;
    if (slot < inventory.getTopInventory().getSize()) {
      switch (inventory.getType()) {
      case FURNACE: 
        if (slot == 2) {
          type = InventoryType.SlotType.RESULT;
        } else if (slot == 1) {
          type = InventoryType.SlotType.FUEL;
        }
        break;
      case BREWING: 
        if (slot == 0) {
          type = InventoryType.SlotType.FUEL;
        } else {
          type = InventoryType.SlotType.CRAFTING;
        }
        break;
      case ENCHANTING: 
        type = InventoryType.SlotType.CRAFTING;
        break;
      case WORKBENCH: 
      case CRAFTING: 
        if (slot == 0) {
          type = InventoryType.SlotType.RESULT;
        } else {
          type = InventoryType.SlotType.CRAFTING;
        }
        break;
      

      }
      
    } else if (slot == 64537) {
      type = InventoryType.SlotType.OUTSIDE;
    } else if ((inventory.getType() == InventoryType.CRAFTING) && (slot < 9)) {
      type = InventoryType.SlotType.ARMOR;
    } else if (slot >= inventory.countSlots() - 9) {
      type = InventoryType.SlotType.QUICKBAR;
    }
    
    return type;
  }
}
