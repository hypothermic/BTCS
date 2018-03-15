package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.craftbukkit.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;



public class InventoryCrafting
  implements IInventory
{
  private ItemStack[] items;
  private int b;
  private Container c;
  public List<HumanEntity> transaction = new ArrayList();
  public CraftingRecipe currentRecipe;
  public IInventory resultInventory;
  private EntityHuman owner;
  private int maxStack = 64;
  
  public ItemStack[] getContents() {
    return this.items;
  }
  
  public void onOpen(CraftHumanEntity who) {
    this.transaction.add(who);
  }
  
  public InventoryType getInvType() {
    return this.items.length == 4 ? InventoryType.CRAFTING : InventoryType.WORKBENCH;
  }
  
  public void onClose(CraftHumanEntity who) {
    this.transaction.remove(who);
  }
  
  public List<HumanEntity> getViewers() {
    return this.transaction;
  }
  
  public InventoryHolder getOwner() {
    return this.owner.getBukkitEntity();
  }
  
  public void setMaxStackSize(int size) {
    this.maxStack = size;
    this.resultInventory.setMaxStackSize(size);
  }
  
  public InventoryCrafting(Container container, int i, int j, EntityHuman player) {
    this(container, i, j);
    this.owner = player;
  }
  
  public InventoryCrafting(Container container, int i, int j)
  {
    int k = i * j;
    
    this.items = new ItemStack[k];
    this.c = container;
    this.b = i;
  }
  
  public int getSize() {
    return this.items.length;
  }
  
  public ItemStack getItem(int i) {
    return i >= getSize() ? null : this.items[i];
  }
  
  public ItemStack b(int i, int j) {
    if ((i >= 0) && (i < this.b)) {
      int k = i + j * this.b;
      
      return getItem(k);
    }
    return null;
  }
  
  public String getName()
  {
    return "container.crafting";
  }
  
  public ItemStack splitWithoutUpdate(int i) {
    if (this.items[i] != null) {
      ItemStack itemstack = this.items[i];
      
      this.items[i] = null;
      return itemstack;
    }
    return null;
  }
  
  public ItemStack splitStack(int i, int j)
  {
    if (this.items[i] != null)
    {

      if (this.items[i].count <= j) {
        ItemStack itemstack = this.items[i];
        this.items[i] = null;
        this.c.a(this);
        return itemstack;
      }
      ItemStack itemstack = this.items[i].a(j);
      if (this.items[i].count == 0) {
        this.items[i] = null;
      }
      
      this.c.a(this);
      return itemstack;
    }
    
    return null;
  }
  
  public void setItem(int i, ItemStack itemstack)
  {
    this.items[i] = itemstack;
    this.c.a(this);
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
