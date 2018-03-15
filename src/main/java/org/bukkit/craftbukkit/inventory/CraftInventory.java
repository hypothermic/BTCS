package org.bukkit.craftbukkit.inventory;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import net.minecraft.server.ContainerEnchantTableInventory;
import net.minecraft.server.IInventory;
import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.PlayerInventory;
import net.minecraft.server.TileEntityBrewingStand;
import net.minecraft.server.TileEntityDispenser;
import net.minecraft.server.TileEntityFurnace;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CraftInventory
  implements Inventory
{
  protected IInventory inventory;
  
  public CraftInventory(IInventory inventory)
  {
    this.inventory = inventory;
  }
  
  public IInventory getInventory() {
    return this.inventory;
  }
  
  public int getSize() {
    return getInventory().getSize();
  }
  
  public String getName() {
    return getInventory().getName();
  }
  
  public org.bukkit.inventory.ItemStack getItem(int index) {
    net.minecraft.server.ItemStack item = getInventory().getItem(index);
    return item == null ? null : new CraftItemStack(item);
  }
  
  public org.bukkit.inventory.ItemStack[] getContents() {
    org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[getSize()];
    net.minecraft.server.ItemStack[] mcItems = getInventory().getContents();
    
    for (int i = 0; i < mcItems.length; i++) {
      items[i] = (mcItems[i] == null ? null : new CraftItemStack(mcItems[i]));
    }
    
    return items;
  }
  
  public void setContents(org.bukkit.inventory.ItemStack[] items) {
    if (getInventory().getContents().length < items.length) {
      throw new IllegalArgumentException("Invalid inventory size; expected " + getInventory().getContents().length + " or less");
    }
    
    net.minecraft.server.ItemStack[] mcItems = getInventory().getContents();
    
    for (int i = 0; i < mcItems.length; i++) {
      if (i >= items.length) {
        mcItems[i] = null;
      } else {
        mcItems[i] = CraftItemStack.createNMSItemStack(items[i]);
      }
    }
  }
  
  public void setItem(int index, org.bukkit.inventory.ItemStack item) {
    getInventory().setItem(index, (item == null) || (item.getTypeId() == 0) ? null : CraftItemStack.createNMSItemStack(item));
  }
  
  public boolean contains(int materialId) {
    for (org.bukkit.inventory.ItemStack item : getContents()) {
      if ((item != null) && (item.getTypeId() == materialId)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean contains(Material material) {
    return contains(material.getId());
  }
  
  public boolean contains(org.bukkit.inventory.ItemStack item) {
    if (item == null) {
      return false;
    }
    for (org.bukkit.inventory.ItemStack i : getContents()) {
      if (item.equals(i)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean contains(int materialId, int amount) {
    int amt = 0;
    for (org.bukkit.inventory.ItemStack item : getContents()) {
      if ((item != null) && (item.getTypeId() == materialId)) {
        amt += item.getAmount();
      }
    }
    return amt >= amount;
  }
  
  public boolean contains(Material material, int amount) {
    return contains(material.getId(), amount);
  }
  
  public boolean contains(org.bukkit.inventory.ItemStack item, int amount) {
    if (item == null) {
      return false;
    }
    int amt = 0;
    for (org.bukkit.inventory.ItemStack i : getContents()) {
      if (item.equals(i)) {
        amt += item.getAmount();
      }
    }
    return amt >= amount;
  }
  
  public HashMap<Integer, org.bukkit.inventory.ItemStack> all(int materialId) {
    HashMap<Integer, org.bukkit.inventory.ItemStack> slots = new HashMap();
    
    org.bukkit.inventory.ItemStack[] inventory = getContents();
    for (int i = 0; i < inventory.length; i++) {
      org.bukkit.inventory.ItemStack item = inventory[i];
      if ((item != null) && (item.getTypeId() == materialId)) {
        slots.put(Integer.valueOf(i), item);
      }
    }
    return slots;
  }
  
  public HashMap<Integer, org.bukkit.inventory.ItemStack> all(Material material) {
    return all(material.getId());
  }
  
  public HashMap<Integer, org.bukkit.inventory.ItemStack> all(org.bukkit.inventory.ItemStack item) {
    HashMap<Integer, org.bukkit.inventory.ItemStack> slots = new HashMap();
    if (item != null) {
      org.bukkit.inventory.ItemStack[] inventory = getContents();
      for (int i = 0; i < inventory.length; i++) {
        if (item.equals(inventory[i])) {
          slots.put(Integer.valueOf(i), inventory[i]);
        }
      }
    }
    return slots;
  }
  
  public int first(int materialId) {
    org.bukkit.inventory.ItemStack[] inventory = getContents();
    for (int i = 0; i < inventory.length; i++) {
      org.bukkit.inventory.ItemStack item = inventory[i];
      if ((item != null) && (item.getTypeId() == materialId)) {
        return i;
      }
    }
    return -1;
  }
  
  public int first(Material material) {
    return first(material.getId());
  }
  
  public int first(org.bukkit.inventory.ItemStack item) {
    return first(item, true);
  }
  
  public int first(org.bukkit.inventory.ItemStack item, boolean withAmount) {
    if (item == null) {
      return -1;
    }
    org.bukkit.inventory.ItemStack[] inventory = getContents();
    for (int i = 0; i < inventory.length; i++) {
      if (inventory[i] != null)
      {
        boolean equals = false;
        
        if (withAmount) {
          equals = item.equals(inventory[i]);
        } else {
          equals = (item.getTypeId() == inventory[i].getTypeId()) && (item.getDurability() == inventory[i].getDurability()) && (item.getEnchantments().equals(inventory[i].getEnchantments()));
        }
        
        if (equals)
          return i;
      }
    }
    return -1;
  }
  
  public int firstEmpty() {
    org.bukkit.inventory.ItemStack[] inventory = getContents();
    for (int i = 0; i < inventory.length; i++) {
      if (inventory[i] == null) {
        return i;
      }
    }
    return -1;
  }
  
  public int firstPartial(int materialId) {
    org.bukkit.inventory.ItemStack[] inventory = getContents();
    for (int i = 0; i < inventory.length; i++) {
      org.bukkit.inventory.ItemStack item = inventory[i];
      if ((item != null) && (item.getTypeId() == materialId) && (item.getAmount() < item.getMaxStackSize())) {
        return i;
      }
    }
    return -1;
  }
  
  public int firstPartial(Material material) {
    return firstPartial(material.getId());
  }
  
  public int firstPartial(org.bukkit.inventory.ItemStack item) {
    org.bukkit.inventory.ItemStack[] inventory = getContents();
    org.bukkit.inventory.ItemStack filteredItem = new CraftItemStack(item);
    if (item == null) {
      return -1;
    }
    for (int i = 0; i < inventory.length; i++) {
      org.bukkit.inventory.ItemStack cItem = inventory[i];
      if ((cItem != null) && (cItem.getTypeId() == filteredItem.getTypeId()) && (cItem.getAmount() < cItem.getMaxStackSize()) && (cItem.getDurability() == filteredItem.getDurability()) && (cItem.getEnchantments().equals(filteredItem.getEnchantments()))) {
        return i;
      }
    }
    return -1;
  }
  
  public HashMap<Integer, org.bukkit.inventory.ItemStack> addItem(org.bukkit.inventory.ItemStack... items) {
    HashMap<Integer, org.bukkit.inventory.ItemStack> leftover = new HashMap();
    






    for (int i = 0; i < items.length; i++) {
      org.bukkit.inventory.ItemStack item = items[i];
      for (;;)
      {
        int firstPartial = firstPartial(item);
        

        if (firstPartial == -1)
        {
          int firstFree = firstEmpty();
          
          if (firstFree == -1)
          {
            leftover.put(Integer.valueOf(i), item);
            break;
          }
          
          if (item.getAmount() > getMaxItemStack()) {
            CraftItemStack stack = new CraftItemStack(item.getTypeId(), getMaxItemStack(), item.getDurability());
            stack.addUnsafeEnchantments(item.getEnchantments());
            setItem(firstFree, stack);
            item.setAmount(item.getAmount() - getMaxItemStack());
          }
          else {
            setItem(firstFree, item);
            break;
          }
        }
        else
        {
          org.bukkit.inventory.ItemStack partialItem = getItem(firstPartial);
          
          int amount = item.getAmount();
          int partialAmount = partialItem.getAmount();
          int maxAmount = partialItem.getMaxStackSize();
          

          if (amount + partialAmount <= maxAmount) {
            partialItem.setAmount(amount + partialAmount);
            break;
          }
          

          partialItem.setAmount(maxAmount);
          item.setAmount(amount + partialAmount - maxAmount);
        }
      }
    }
    return leftover;
  }
  
  public HashMap<Integer, org.bukkit.inventory.ItemStack> removeItem(org.bukkit.inventory.ItemStack... items) {
    HashMap<Integer, org.bukkit.inventory.ItemStack> leftover = new HashMap();
    


    for (int i = 0; i < items.length; i++) {
      org.bukkit.inventory.ItemStack item = items[i];
      int toDelete = item.getAmount();
      for (;;)
      {
        int first = first(item, false);
        

        if (first == -1) {
          item.setAmount(toDelete);
          leftover.put(Integer.valueOf(i), item);
        }
        else {
          org.bukkit.inventory.ItemStack itemStack = getItem(first);
          int amount = itemStack.getAmount();
          
          if (amount <= toDelete) {
            toDelete -= amount;
            
            clear(first);
          }
          else {
            itemStack.setAmount(amount - toDelete);
            setItem(first, itemStack);
            toDelete = 0;
          }
          


          if (toDelete <= 0)
            break;
        }
      }
    }
    return leftover;
  }
  
  private int getMaxItemStack() {
    return getInventory().getMaxStackSize();
  }
  
  public void remove(int materialId) {
    org.bukkit.inventory.ItemStack[] items = getContents();
    for (int i = 0; i < items.length; i++) {
      if ((items[i] != null) && (items[i].getTypeId() == materialId)) {
        clear(i);
      }
    }
  }
  
  public void remove(Material material) {
    remove(material.getId());
  }
  
  public void remove(org.bukkit.inventory.ItemStack item) {
    org.bukkit.inventory.ItemStack[] items = getContents();
    for (int i = 0; i < items.length; i++) {
      if ((items[i] != null) && (items[i].equals(item))) {
        clear(i);
      }
    }
  }
  
  public void clear(int index) {
    setItem(index, null);
  }
  
  public void clear() {
    for (int i = 0; i < getSize(); i++) {
      clear(i);
    }
  }
  
  public ListIterator<org.bukkit.inventory.ItemStack> iterator() {
    return new InventoryIterator(this);
  }
  
  public ListIterator<org.bukkit.inventory.ItemStack> iterator(int index) {
    if (index < 0) {
      index += getSize() + 1;
    }
    return new InventoryIterator(this, index);
  }
  
  public List<HumanEntity> getViewers() {
    return this.inventory.getViewers();
  }
  
  public String getTitle() {
    return this.inventory.getName();
  }
  
  public InventoryType getType() {
    if ((this.inventory instanceof InventoryCrafting))
      return this.inventory.getSize() >= 9 ? InventoryType.WORKBENCH : InventoryType.CRAFTING;
    if ((this.inventory instanceof PlayerInventory))
      return InventoryType.PLAYER;
    if ((this.inventory instanceof TileEntityDispenser))
      return InventoryType.DISPENSER;
    if ((this.inventory instanceof TileEntityFurnace))
      return InventoryType.FURNACE;
    if ((this.inventory instanceof ContainerEnchantTableInventory))
      return InventoryType.ENCHANTING;
    if ((this.inventory instanceof TileEntityBrewingStand))
      return InventoryType.BREWING;
    if ((this.inventory instanceof CraftInventoryCustom.MinecraftInventory)) {
      return ((CraftInventoryCustom.MinecraftInventory)this.inventory).getType();
    }
    return InventoryType.CHEST;
  }
  
  public InventoryHolder getHolder()
  {
    return this.inventory.getOwner();
  }
  
  public int getMaxStackSize() {
    return this.inventory.getMaxStackSize();
  }
  
  public void setMaxStackSize(int size) {
    this.inventory.setMaxStackSize(size);
  }
}
