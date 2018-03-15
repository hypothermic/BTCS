package org.bukkit.craftbukkit.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

public class CraftInventoryCustom extends CraftInventory
{
  public CraftInventoryCustom(InventoryHolder owner, InventoryType type)
  {
    super(new MinecraftInventory(owner, type));
  }
  
  public CraftInventoryCustom(InventoryHolder owner, int size) {
    super(new MinecraftInventory(owner, size));
  }
  
  public CraftInventoryCustom(InventoryHolder owner, int size, String title) {
    super(new MinecraftInventory(owner, size, title));
  }
  
  static class MinecraftInventory implements IInventory {
    private ItemStack[] items;
    private int maxStack = 64;
    private List<HumanEntity> viewers;
    private String title;
    private InventoryType type;
    private InventoryHolder owner;
    
    public MinecraftInventory(InventoryHolder owner, InventoryType type) {
      this(owner, type.getDefaultSize(), type.getDefaultTitle());
      this.type = type;
    }
    
    public MinecraftInventory(InventoryHolder owner, int size) {
      this(owner, size, "Chest");
    }
    
    public MinecraftInventory(InventoryHolder owner, int size, String title) {
      this.items = new ItemStack[size];
      this.title = title;
      this.viewers = new ArrayList();
      this.owner = owner;
      this.type = InventoryType.CHEST;
    }
    
    public int getSize() {
      return this.items.length;
    }
    
    public ItemStack getItem(int i) {
      return this.items[i];
    }
    
    public ItemStack splitStack(int i, int j) {
      ItemStack stack = getItem(i);
      
      if (stack == null) return null;
      ItemStack result;
      if (stack.count <= j) {
        setItem(i, null);
        result = stack;
      } else {
        result = new ItemStack(stack.id, j, stack.getData(), stack.getEnchantments());
        stack.count -= j;
      }
      this.update();
      return result;
    }
    
    public ItemStack splitWithoutUpdate(int i) {
      ItemStack stack = getItem(i);
      
      if (stack == null) return null;
      ItemStack result;
      if (stack.count <= 1) {
        setItem(i, null);
        result = stack;
      } else {
        result = new ItemStack(stack.id, 1, stack.getData(), stack.getEnchantments());
        stack.count -= 1;
      }
      return result;
    }
    
    public void setItem(int i, ItemStack itemstack) {
      this.items[i] = itemstack;
      if ((itemstack != null) && (getMaxStackSize() > 0) && (itemstack.count > getMaxStackSize())) {
        itemstack.count = getMaxStackSize();
      }
    }
    
    public String getName() {
      return this.title;
    }
    
    public int getMaxStackSize() {
      return this.maxStack;
    }
    
    public void setMaxStackSize(int size) {
      this.maxStack = size;
    }
    
    public void update() {}
    
    public boolean a(EntityHuman entityhuman) {
      return true;
    }
    
    public ItemStack[] getContents() {
      return this.items;
    }
    
    public void onOpen(CraftHumanEntity who) {
      this.viewers.add(who);
    }
    
    public void onClose(CraftHumanEntity who) {
      this.viewers.remove(who);
    }
    
    public List<HumanEntity> getViewers() {
      return this.viewers;
    }
    
    public InventoryType getType() {
      return this.type;
    }
    
    public void f() {}
    
    public void g() {}
    
    public InventoryHolder getOwner() {
      return this.owner;
    }
  }
}
